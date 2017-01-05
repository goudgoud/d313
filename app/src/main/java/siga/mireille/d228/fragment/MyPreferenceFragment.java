package siga.mireille.d228.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import siga.mireille.d228.R;

/**
 * MyPreferenceFragment class
 *
 * @author SIGA Mireille
 * @version 1
 * @since 23 /12/2016
 */
public class MyPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}