package goudard.david.qcm;

import android.content.Context;

import org.json.JSONException;

/**
 * Created by david on 23/12/16.
 */

class QcmStorageManager {

    static Qcm loadQcm(Context context) {
        return (Qcm) SerializableManager.readSerializable(context, "qcm.tmp");
    }

    static void saveQcm(Context context, Qcm qcm) {
        SerializableManager.removeSerializable(context, "qcm.tmp");
        SerializableManager.saveSerializable(context, qcm, "qcm.tmp");
    }

    static Qcm downloadQcm(Context context) throws JSONException {
        Qcm qcm = null;
        if (Internet.isNetworkConnectivity(context)) {
            if (Internet.isNetworkAvailable(context)) {
                QcmJsonParser qcmParser = new QcmJsonParser(context);
                qcm = qcmParser.getQcm();
            }
        }
        return qcm;
    }
}
