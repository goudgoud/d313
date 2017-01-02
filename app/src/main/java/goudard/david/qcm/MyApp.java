package goudard.david.qcm;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Qcm
 * Created by David GOUDARD
 */
public class MyApp extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
