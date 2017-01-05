package siga.mireille.d228.manager;

import android.content.Context;

import org.json.JSONException;

import siga.mireille.d228.entity.Qcm;
import siga.mireille.d228.tools.Network;
import siga.mireille.d228.tools.QcmJsonParser;

/**
 * Tests storage manager
 *
 * @author SIGA Mireille
 * @version 1
 * @since 23/12/2016
 */
public class QcmStorageManager {

    /**
     * Load tests
     *
     * @param context Context in use
     * @return Qcm qcm
     */
    public static Qcm loadQcm(Context context) {
        return (Qcm) SerializableManager.readSerializable(context, "qcm.tmp");
    }

    /**
     * Save tests
     *
     * @param context Context in use
     * @param qcm     Qcm
     * @return boolean boolean
     */
    static public boolean saveQcm(Context context, Qcm qcm) {
        try {
            SerializableManager.removeSerializable(context, "qcm.tmp");
            SerializableManager.saveSerializable(context, qcm, "qcm.tmp");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Download tests from server
     *
     * @param context Context in use
     * @return Qcm qcm
     * @throws JSONException the json exception
     */
    static public Qcm downloadQcm(Context context) throws Exception {
        Qcm qcm = null;

        if (Network.isNetworkAvailable(context)) {
            if (Network.isNetworkConnectivity(context)) {
                QcmJsonParser qcmParser = new QcmJsonParser(context);
                qcm = qcmParser.getQcm();
            } else {
                throw new Exception(Network.ERR_INTERNET);
                //Toast.makeText(context, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
            }
        } else {
            throw new Exception(Network.ERR_NETWORK);
            //Toast.makeText(context, "No network available", Toast.LENGTH_LONG).show();
        }
        return qcm;
    }
}
