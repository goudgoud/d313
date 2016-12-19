package goudard.david.qcm;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Target;
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
    private MainActivity mainActivity;

    public void QcmJsonParser(final MainActivity context) {
        mainActivity = context;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, QCM_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        qcm = jsonParseObject(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mainActivity.getTvMessageSystem().setText("error" + error.toString());
                    }
                });

        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public Qcm getQcm() {
        return this.qcm;
    }

    private Qcm jsonParseObject(JSONObject json) {

        String pack = null;
        try {
            //jsonObj = json.getJSONObject("Android");
            listeCategories.clear();

            Iterator<String> keys = json.keys();
            Log.d("****************", "*******************");
            while (keys.hasNext()) {
                String key = keys.next();
                Log.d("cat key", key);
                pack += key + "\n";
                listeCategories.add(key);

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
