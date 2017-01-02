package goudard.david.qcm.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import goudard.david.qcm.R;

/**
 * MyPreferenceFragment class
 *
 * @author David GOUDARD
 * @version 1
 * @since 23/12/2016
 */
public class MyPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}