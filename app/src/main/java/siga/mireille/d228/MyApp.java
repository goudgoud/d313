package siga.mireille.d228;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Qcm
 * Created by SIGA Mireille
 */
public class MyApp extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
