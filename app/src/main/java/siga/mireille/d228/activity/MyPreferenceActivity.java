package siga.mireille.d228.activity;

import android.preference.PreferenceActivity;

import java.util.List;

import siga.mireille.d228.R;
import siga.mireille.d228.fragment.MyPreferenceFragment;

/**
 * Activity to manage preferences of application
 *
 * @author SIGA Mireille
 * @version 1
 * @since 23 /12/2106
 */
public class MyPreferenceActivity extends PreferenceActivity {

    /**
     *
     * @param target List of headers preferences
     */
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_preference, target);
    }

    /**
     *
     * @param fragmentName String, then name of fragment
     * @return boolean
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return MyPreferenceFragment.class.getName().equals(fragmentName);
    }
}
