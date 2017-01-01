package goudard.david.qcm.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by david on 21/12/16.
 */

public class Internet {

    public static boolean isNetworkConnectivity(Context context) {
        boolean value = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Le type de connexion, réseau ou Wi-Fi.
        int networkType = networkInfo.getType();

        // Vérification de l’état de la connexion.
        NetworkInfo.State networkState = networkInfo.getState();
        if (networkState.compareTo(NetworkInfo.State.CONNECTED) != 0) {
            Toast.makeText(context, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
            value = false;
        }
        return value;
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean value = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info == null | info.isAvailable() == false) {
            Toast.makeText(context, "No network available", Toast.LENGTH_LONG).show();
        } else if (info != null && info.isAvailable()) {
            value = true;
        }
        return value;
    }
}
