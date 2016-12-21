package goudard.david.qcm;

import android.content.Context;
import android.os.StrictMode;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestTickle;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.JsonRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.VolleyTickle;

/**
 * Created by david on 19/12/16.
 */

public class QcmJsonParser {

    private static final String QCM_URL = "http://daviddurand.info/D228/qcm";
    private Qcm qcm;
    private Survey survey;
    private TextView tv;
    private boolean loading = false;
    private Context context;


    public QcmJsonParser(Context m, TextView tv) throws JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.tv = tv;
        this.context = m;
        loadQcm();
    }

    public Qcm getQcm() {
        while (loading) {
        }
        return this.qcm;
    }

    private void loadQcm() throws JSONException {
        loading = true;
        try {
            RequestTickle mRequestTickle = VolleyTickle.newRequestTickle(context.getApplicationContext());

            JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, QCM_URL, null, null, null);
            mRequestTickle.add(jsonRequest);
            NetworkResponse response = mRequestTickle.start();

            if (response.statusCode == 200) {
                String data = VolleyTickle.parseResponse(response);
                JSONObject jsonData = new JSONObject(data);
                qcm = loadSurveyFamilies(qcm, jsonData);
                loading = false;

            } else {

                throw new Exception("Erreur " + response.statusCode);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Qcm loadSurveyFamilies(Qcm qcm, JSONObject jsonResponse) {
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
            tv.setText("error " + e.toString());
        } catch (Exception e) {
            tv.setText("error " + e.toString());
            e.printStackTrace();
        }
        tv.setText("Terminé");
        return qcm;
    }

    private SurveyFamily loadSurveys(final SurveyFamily surveyFamily, JSONObject jsonSurveys) throws Exception {
        Iterator<String> iteratorSurvey = jsonSurveys.keys();
            while (iteratorSurvey.hasNext()) {
                String code = iteratorSurvey.next();
                String name = jsonSurveys.getString(code);
                survey = new Survey();
                survey.setCode(code).setName(name);

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

    private Survey loadQuestions(Survey survey, JSONArray jsonQuestions) {
        try {
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
                question.setScore(0);

                survey.addQuestion(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tv.setText("error " + e.toString());
        }
        return survey;
    }

}
