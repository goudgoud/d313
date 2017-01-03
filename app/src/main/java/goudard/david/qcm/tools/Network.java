package goudard.david.qcm.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Network class
 *
 * @author David GOUDARD
 * @version 1.0.1
 * @since 03/01/2017
 */
public class Network {

    public static final String ERR_NETWORK = "ERR_NETWORK";
    public static final String ERR_INTERNET = "ERR_INTERNET";

    /**
     * Is network connectivity boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkConnectivity(Context context) {
        boolean value = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            // Le type de connexion, réseau ou Wi-Fi.
            int networkType = networkInfo.getType();

            // Vérification de l’état de la connexion.
            NetworkInfo.State networkState = networkInfo.getState();
            if (networkState.compareTo(NetworkInfo.State.CONNECTED) == 0) {
                value = true;
            }
        }
        return value;
    }

    /**
     * Is network available boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean value = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null) {
            if (info.isAvailable()) {
                value = true;
            }
        }
        return value;
    }
}
