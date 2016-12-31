package goudard.david.qcm.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import goudard.david.qcm.entity.Qcm;
import goudard.david.qcm.manager.QcmStorageManager;
import goudard.david.qcm.R;
import goudard.david.qcm.entity.SurveyFamily;
import goudard.david.qcm.adapter.SurveyFamilyAdapter;
import goudard.david.qcm.adapter.SurveyFamilyAdapterListenerInterface;
import goudard.david.qcm.fragment.MainInternalFragment;

import static goudard.david.qcm.activity.SurveyFamilyActivity.KEY_FROM_SURVEY_FAMILY;

public class MainActivity extends AppCompatActivity implements SurveyFamilyAdapterListenerInterface {
    static final String KEY_FROM_MAIN = "KEY_FROM_MAIN";
    static final int RQC_SURVEY_FAMILY = 101;
    Toolbar toolbar;
    private TextView tvMessageSystem = null;
    private Qcm qcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = getTvMessageSystem();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getString(R.string.app_name));

        initFragment();
        initBottomToolBar();
        loadQcm();

    }

    private void loadQcm() {
        this.qcm = QcmStorageManager.loadQcm(this);
        // if qcm doesn't exist on disk, load from internet
        if (this.qcm == null) {
            downloadQcm();
        }

    }

    private void saveQcm() {
        QcmStorageManager.saveQcm(this, this.qcm);
    }

    private void downloadQcm() {
        try {



            this.qcm = QcmStorageManager.downloadQcm(this);
            saveQcm();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initFragment() {
        final MainInternalFragment fragment = new MainInternalFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainActivityframe, fragment, "square")
                .commit();
    }

    private void initBottomToolBar() {
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        assert bottomNavigation != null;

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.download_qcm), R.drawable.ic_action_download, Color.GRAY);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.score), R.drawable.ic_action_score, Color.GRAY);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.save), R.drawable.ic_action_save, Color.GRAY);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#F63D2B"));
        //bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        //  Enables Reveal effect
        bottomNavigation.setColored(true);
        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:
                        downloadQcm();
                        break;
                    case 1:
                        showScore();
                        break;
                    case 2:
                        saveQcm();
                        break;
                }

            }
        });

    }


    private void showScore() {

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
                intent.setClassName(this, "goudard.david.qcm.activity.MyPreferenceActivity");
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
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
