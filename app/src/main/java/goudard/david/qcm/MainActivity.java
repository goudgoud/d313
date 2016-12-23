package goudard.david.qcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private TextView tvMessageSystem = null;
    private Qcm qcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences: {
                /*
                getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
                //*/
                //*
                Intent intent = new Intent();
                intent.setClassName(this, "goudard.david.qcm.MyPreferenceActivity");
                startActivity(intent);
                return true;
                //*/
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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
