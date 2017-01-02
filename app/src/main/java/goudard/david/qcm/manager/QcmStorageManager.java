package goudard.david.qcm.manager;

import android.content.Context;

import org.json.JSONException;

import goudard.david.qcm.entity.Qcm;
import goudard.david.qcm.tools.Network;
import goudard.david.qcm.tools.QcmJsonParser;

/**
 * Created by david on 23/12/16.
 */

/**
 * Tests storage manager
 *
 * @author David GOUDARD
 * @version 1
 * @since 23/12/2016
 */
public class QcmStorageManager {

    /**
     * Load tests
     *
     * @param context Context in use
     * @return Qcm
     */
    public static Qcm loadQcm(Context context) {
        return (Qcm) SerializableManager.readSerializable(context, "qcm.tmp");
    }

    /**
     * Save tests
     *
     * @param context Context in use
     * @param qcm     Qcm
     * @return boolean
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
     * @return Qcm
     * @throws JSONException
     */
    static public Qcm downloadQcm(Context context) throws JSONException {
        Qcm qcm = null;

        if (Network.isNetworkAvailable(context)) {
            if (Network.isNetworkConnectivity(context)) {
                QcmJsonParser qcmParser = new QcmJsonParser(context);
                qcm = qcmParser.getQcm();
            }
        }
        return qcm;
    }
}
