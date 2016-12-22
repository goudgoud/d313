package goudard.david.qcm;

import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private BottomBar mBottomBar;
    private TextView tvMessageSystem = null;
    private Qcm qcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favorites) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView tv = getTvMessageSystem();
        try {
            this.qcm = loadQcm();
            // if qcm doesn't exist on disk, load from internet
            if (this.qcm == null) {
                this.qcm = downloadQcm();
                saveQcm(this.qcm);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TextView getTvMessageSystem() {
        if (tvMessageSystem == null) {
            tvMessageSystem = (TextView) findViewById(R.id.tvMainActivity_MessageSystem);
        }
        return tvMessageSystem;
    }

    private Qcm loadQcm() {
        return (Qcm) SerializableManager.readSerializable(this, "qcm.er");
    }

    private void saveQcm(Qcm qcm) {
        SerializableManager.saveSerializable(this, qcm, "qcm.er");
    }

    private Qcm downloadQcm() throws JSONException {
        Qcm qcm = null;
        if (Internet.isNetworkConnectivity(this)) {
            if (Internet.isNetworkAvailable(this)) {
                QcmJsonParser qcmParser = new QcmJsonParser(this, tvMessageSystem);
                qcm = qcmParser.getQcm();
            }
        }
        return qcm;
    }
}
