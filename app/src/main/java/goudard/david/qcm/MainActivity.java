package goudard.david.qcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import static goudard.david.qcm.IO_Qcm.readFromFile;

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
            qcm = IO_Qcm.readFromFile(this, tv);
            // if qcm doesn't exist on disk, load from internet
            if (qcm == null) {
                loadQcm();
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

    private void loadQcm() throws JSONException {
        if (Internet.isNetworkConnectivity(this)) {
            if (Internet.isNetworkAvailable(this)) {
                QcmJsonParser qcmParser = new QcmJsonParser(this, tvMessageSystem);
                qcm = qcmParser.getQcm();
                IO_Qcm io = new IO_Qcm(this, tvMessageSystem);
                io.saveToFile(qcm);
            }
        }
    }
}
