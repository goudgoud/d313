package goudard.david.qcm.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import goudard.david.qcm.R;

/**
 * Created by david on 23/12/16.
 */

public class MyPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}