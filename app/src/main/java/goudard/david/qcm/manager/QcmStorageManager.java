package goudard.david.qcm.manager;

import android.content.Context;

import org.json.JSONException;

import goudard.david.qcm.tools.Internet;
import goudard.david.qcm.entity.Qcm;
import goudard.david.qcm.tools.QcmJsonParser;

/**
 * Created by david on 23/12/16.
 */

public class QcmStorageManager {

    public static Qcm loadQcm(Context context) {
        return (Qcm) SerializableManager.readSerializable(context, "qcm.tmp");
    }

    static public void saveQcm(Context context, Qcm qcm) {
        SerializableManager.removeSerializable(context, "qcm.tmp");
        SerializableManager.saveSerializable(context, qcm, "qcm.tmp");
    }

    static public Qcm downloadQcm(Context context) throws JSONException {
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
