package goudard.david.qcm.tools;

import android.content.Context;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestTickle;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.toolbox.VolleyTickle;

import goudard.david.qcm.entity.Qcm;
import goudard.david.qcm.entity.Question;
import goudard.david.qcm.entity.Survey;
import goudard.david.qcm.entity.SurveyFamily;

/**
 * Created by david on 19/12/16.
 */
public class QcmJsonParser {

    private static final String QCM_URL = "http://daviddurand.info/D228/qcm";
    private Qcm qcm;
    private TextView tv;
    private boolean loading = false;
    private Context context;


    /**
     * Instantiates a new Qcm json parser.
     *
     * @param m the m
     * @throws JSONException the json exception
     */
    public QcmJsonParser(Context m) throws JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.context = m;
        downloadQcm();
    }

    /**
     * Gets qcm.
     *
     * @return the qcm
     */
    public Qcm getQcm() {
        return this.qcm;
    }

    private void downloadQcm() throws JSONException {
        qcm = new Qcm();

        try {
            RequestTickle mRequestTickle = VolleyTickle.newRequestTickle(context.getApplicationContext());

            JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, QCM_URL, null, null, null);
            mRequestTickle.add(jsonRequest);
            NetworkResponse response = mRequestTickle.start();

            if (response.statusCode == 200) {
                String data = VolleyTickle.parseResponse(response);
                JSONObject jsonData = new JSONObject(data);
                qcm = loadSurveyFamilies(qcm, jsonData);
            } else {
                throw new Exception("Erreur " + response.statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        } finally {

        }
    }

    private Qcm loadSurveyFamilies(Qcm qcm, JSONObject jsonResponse) throws Exception {
        try {
            Iterator<String> iteratorSurveyFamily = jsonResponse.keys();

            while (iteratorSurveyFamily.hasNext()) {
                String surveyFamilyName = iteratorSurveyFamily.next();
                SurveyFamily surveyFamily = new SurveyFamily();
                surveyFamily.setName(surveyFamilyName);

                JSONObject jsonSurveys = jsonResponse.getJSONObject(surveyFamilyName);
                surveyFamily = loadSurveys(surveyFamily, jsonSurveys);
                qcm.addFamilleQuestionnaire(surveyFamily);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        return qcm;
    }

    private SurveyFamily loadSurveys(final SurveyFamily surveyFamily, JSONObject jsonSurveys) throws Exception {
        Iterator<String> iteratorSurvey = jsonSurveys.keys();
        while (iteratorSurvey.hasNext()) {
            String code = iteratorSurvey.next();
            String name = jsonSurveys.getString(code);
            Survey survey = new Survey();
            survey.setCode(code).setName(name);
            survey.setScore(0);

            RequestTickle mRequestTickle = VolleyTickle.newRequestTickle(context.getApplicationContext());

            JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, QCM_URL + "/?action=pack&pack=" + code, null, null, null);
            mRequestTickle.add(jsonRequest);
            NetworkResponse response = mRequestTickle.start();

            if (response.statusCode == 200) {
                String data = VolleyTickle.parseResponse(response);
                JSONObject jsonData = new JSONObject(data);
                survey = loadQuestions(survey, jsonData.getJSONArray("questions"));
                surveyFamily.addQuestionnaire(survey);

            } else {
                throw new Exception("Erreur " + response.statusCode);
            }
        }
        return surveyFamily;
    }

    private Survey loadQuestions(Survey survey, JSONArray jsonQuestions) throws JSONException {

        for (int n = 0; n < jsonQuestions.length(); n++) {
            JSONObject jsonQuestion = jsonQuestions.getJSONObject(n);
            String titre = jsonQuestion.getString("titre");
            JSONArray jsonChoix = jsonQuestion.getJSONArray("choix");
            ArrayList<String> choix = new ArrayList<>();

            for (int i = 0; i < jsonChoix.length(); i++) {
                String unChoix = jsonChoix.getString(i);
                choix.add(unChoix.toString());
            }
            int correct = jsonQuestion.getInt("correct");

            Question question = new Question();
            question.setTitre(titre);
            question.setChoix(choix);
            question.setCorrect(correct);

            survey.addQuestion(question);
        }

        return survey;
    }

}
