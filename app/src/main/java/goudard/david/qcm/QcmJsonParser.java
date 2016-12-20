package goudard.david.qcm;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Iterator;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Created by david on 19/12/16.
 */

public class QcmJsonParser {

    private static final String QCM_URL = "http://daviddurand.info/D228/qcm";
    private Qcm qcm;
    private Survey survey;
    private MainActivity mainActivity;
    private boolean loading = false;

    public void QcmJsonParser(final MainActivity context) {
        mainActivity = context;
        loadQcm();
    }

    public Qcm getQcm() {
        while (loading) {
        }
        return this.qcm;
    }


    private void loadQcm() {
        loading = true;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, QCM_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        qcm = loadSurveyFamilies(qcm, response);
                        loading = false;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mainActivity.getTvMessageSystem().setText("error " + error.toString());
                        qcm = null;
                        loading = false;
                    }
                });

        MySingleton.getInstance(mainActivity).addToRequestQueue(jsObjRequest);
    }

    private Qcm loadSurveyFamilies(Qcm qcm, JSONObject jsonResponse) {
        try {
            Iterator<String> iteratorSurveyFamily = jsonResponse.keys();

            while (iteratorSurveyFamily.hasNext()) {
                String surveyFamilyName = iteratorSurveyFamily.next();
                SurveyFamily surveyFamily = new SurveyFamily();
                surveyFamily.setName(surveyFamilyName);

                JSONObject jsonSurveys = jsonResponse.getJSONObject(surveyFamilyName);
                loadSurveys(surveyFamily, jsonSurveys);
                qcm.addFamilleQuestionnaire(surveyFamily);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            mainActivity.getTvMessageSystem().setText("error " + e.toString());
        }

        return qcm;
    }

    private SurveyFamily loadSurveys(SurveyFamily surveyFamily, JSONObject jsonSurveys) {
        try {

            Iterator<String> iteratorSurvey = jsonSurveys.keys();
            while (iteratorSurvey.hasNext()) {
                String code = iteratorSurvey.next();
                String name = jsonSurveys.getString(code);
                survey = new Survey();
                survey.setCode(code).setName(name);

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, QCM_URL + "/?action=pack&pack=" + code, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    survey = loadQuestions(survey, response.getJSONArray("questions"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    qcm = null;
                                    loading = false;
                                    mainActivity.getTvMessageSystem().setText("error " + e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mainActivity.getTvMessageSystem().setText("error " + error.toString());
                                qcm = null;
                                loading = false;
                            }
                        });

                MySingleton.getInstance(mainActivity).addToRequestQueue(jsObjRequest);

                surveyFamily.addQuestionnaire(survey);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mainActivity.getTvMessageSystem().setText("error " + e.toString());
        }
        return surveyFamily;
    }

    private Survey loadQuestions(Survey survey, JSONArray jsonQuestions) {
        try {
            for (int n = 0; n < jsonQuestions.length(); n++) {
                JSONObject jsonQuestion = jsonQuestions.getJSONObject(n);
                String titre = jsonQuestion.getString("titre");
                JSONArray jsonChoix = jsonQuestion.getJSONArray("choix");

            }
        } catch (Exception e) {
            e.printStackTrace();
            mainActivity.getTvMessageSystem().setText("error " + e.toString());
        }
    }

    private ArrayList<String> jsonParseObject(JSONObject json) {

        ArrayList<String> pack = new ArrayList<>();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                pack.add(key);

                JSONObject innerJson = json.getJSONObject(key);
                Iterator<String> innerKeys = innerJson.keys();
                while (innerKeys.hasNext()) {
                    String innerKey = innerKeys.next();
                    Log.d("subkey", innerKey);
                    String strQuestionnaire = innerJson.getString(innerKey);
                    pack += strQuestionnaire + "\n";
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pack; //jsonObj.toString();
    }
}
