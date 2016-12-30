package goudard.david.qcm.activity;

import android.preference.PreferenceActivity;

import java.util.List;

import goudard.david.qcm.R;
import goudard.david.qcm.fragment.MyPreferenceFragment;

/**
 * Created by david on 23/12/16.
 */

public class MyPreferenceActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_preference, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return MyPreferenceFragment.class.getName().equals(fragmentName);
    }
}
