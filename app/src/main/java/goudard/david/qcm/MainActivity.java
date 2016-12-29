package goudard.david.qcm;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static goudard.david.qcm.SurveyActivity.KEY_FROM_SURVEY;
import static goudard.david.qcm.SurveyFamilyActivity.KEY_FROM_SURVEY_FAMILY;

public class MainActivity extends AppCompatActivity implements SurveyFamilyAdapterListenerInterface {

    static final String KEY_FROM_MAIN = "KEY_FROM_MAIN";
    static final int RQC_SURVEY_FAMILY = 101;

    private TextView tvMessageSystem = null;
    private Qcm qcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = getTvMessageSystem();
        try {
            this.qcm = QcmStorageManager.loadQcm(this);
            // if qcm doesn't exist on disk, load from internet
            if (this.qcm == null) {
                this.qcm = QcmStorageManager.downloadQcm(this);
                QcmStorageManager.saveQcm(this, this.qcm);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initListView_Qcm();
    }

    protected void onRestart() {
        super.onRestart();
        this.qcm = QcmStorageManager.loadQcm(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        QcmStorageManager.saveQcm(this, this.qcm);
    }

    private void initListView_Qcm() {
        //Récupération de la liste des personnes
        ArrayList<SurveyFamily> listSurveyFamily = this.qcm.getFamilleQuestionnaire();

        //Création et initialisation de l'Adapter pour les personnes
        SurveyFamilyAdapter adapter = new SurveyFamilyAdapter(this, listSurveyFamily);

        //Ecoute des évènements sur la liste
        adapter.addListener(this);

        //Récupération du composant ListView
        ListView list = (ListView) findViewById(R.id.lvMainActivity_Qcm);

        //Initialisation de la liste avec les données
        list.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences: {
                Intent intent = new Intent();
                intent.setClassName(this, "goudard.david.qcm.MyPreferenceActivity");
                startActivity(intent);
                return true;
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


    public void onClickSurveyFamily(SurveyFamily item, int position) {
        Intent myIntent = new Intent(MainActivity.this, SurveyFamilyActivity.class);
        //this.surveyFamily = item;
        myIntent.putExtra(KEY_FROM_MAIN, item);
        startActivityForResult(myIntent, RQC_SURVEY_FAMILY);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Resources res = getResources();
        if (requestCode == RQC_SURVEY_FAMILY && resultCode == RESULT_OK) {
            SurveyFamily surveyFamily = (SurveyFamily) data.getSerializableExtra(KEY_FROM_SURVEY_FAMILY);
            // store surveyFamily updated
            // store survey updated
            ArrayList<SurveyFamily> familleQuestionnaires = this.qcm.getFamilleQuestionnaire();
            Iterator iterator = familleQuestionnaires.iterator();
            int idx = -1;
            while (iterator.hasNext()) {
                idx++;
                if (Objects.equals(familleQuestionnaires.get(idx).getName(), surveyFamily.getName())) {
                    familleQuestionnaires.set(idx, surveyFamily);
                    this.qcm.setFamilleQuestionnaire(familleQuestionnaires);
                    QcmStorageManager.saveQcm(this, this.qcm);
                    break;
                }
            }
        }
    }

    public TextView getTvMessageSystem() {
        if (tvMessageSystem == null) {
            tvMessageSystem = (TextView) findViewById(R.id.tvMainActivity_MessageSystem);
        }
        return tvMessageSystem;
    }

}
