package goudard.david.qcm.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import static goudard.david.qcm.R.string.download_qcm;
import static goudard.david.qcm.activity.SurveyFamilyActivity.KEY_FROM_SURVEY_FAMILY;

public class MainActivity extends AppCompatActivity implements SurveyFamilyAdapterListenerInterface {

    public static final int MSG_ERR = 0;
    public static final int MSG_CNF = 1;
    public static final int MSG_IND = 2;
    public static final String TAG = "MainActivity";
    static final String KEY_FROM_MAIN = "KEY_FROM_MAIN";
    static final int RQC_SURVEY_FAMILY = 101;
    protected ProgressDialog mProgressDialog;
    Toolbar toolbar;

    ;
    private Context mContext;

    /*
    * Handler pour éviter l'erreur
    * CalledFromWrongThreadException :
    * Only the original thread that created a view hierarchy can touch its views
     */
    final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String text2display = null;
            switch (msg.what) {
                case MSG_IND:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.setMessage(((String) msg.obj));
                    }
                    break;
                case MSG_ERR:
                    text2display = (String) msg.obj;
                    Toast.makeText(mContext, "Error: " + text2display,
                            Toast.LENGTH_LONG).show();
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                case MSG_CNF:
                    text2display = (String) msg.obj;
                    Toast.makeText(mContext, "Info: " + text2display,
                            Toast.LENGTH_LONG).show();
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                default: // should never happen
                    break;
            }
        }
    };
    private ErrorStatus status;
    private TextView tvMessageSystem = null;
    private Qcm qcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);

        super.onCreate(savedInstanceState);
        mContext = this;

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

    private ErrorStatus saveQcm() {
        if (QcmStorageManager.saveQcm(this, this.qcm)) {
            return ErrorStatus.NO_ERROR;
        } else {
            return ErrorStatus.ERROR_SAVE;
        }
    }

    private ErrorStatus runDownloadQcm() throws JSONException {
        this.qcm = QcmStorageManager.downloadQcm(this);
        return (this.qcm == null ? ErrorStatus.ERROR_DOWNLOAD : ErrorStatus.NO_ERROR);
    }

    private void downloadQcm() {
        mProgressDialog = ProgressDialog.show(this, getString(R.string.please_wait),
                "...", true);

        new Thread((new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                String progressBarData = getString(R.string.download_qcm);

                // populates the message
                msg = mHandler.obtainMessage(MSG_IND, (Object) progressBarData);
                // sends the message to our handler
                mHandler.sendMessage(msg);

                try {
                    status = runDownloadQcm();
                } catch (JSONException e) {
                    status = ErrorStatus.ERROR_DOWNLOAD;
                    e.printStackTrace();
                }
                if (ErrorStatus.NO_ERROR != status) {
                    Log.e(TAG, getString(R.string.error_download) + status);
                    // error management, creates an error message
                    msg = mHandler.obtainMessage(MSG_ERR,
                            getString(R.string.error_download) + status);
                    // sends the message to our handler
                    mHandler.sendMessage(msg);
                } else {
                    progressBarData = getString(R.string.save);
                    // populates the message
                    msg = mHandler.obtainMessage(MSG_IND, (Object) progressBarData);
                    // sends the message to our handler
                    mHandler.sendMessage(msg);
                    status = saveQcm();

                    if (ErrorStatus.NO_ERROR != status) {
                        Log.e(TAG, getString(R.string.error_save) + status);
                        // error management,creates an error message
                        msg = mHandler.obtainMessage(MSG_ERR, getString(R.string.error_save)
                                + status);
                        mHandler.sendMessage(msg);
                    } else {
                        msg = mHandler.obtainMessage(MSG_CNF, getString(R.string.download_ok));
                        mHandler.sendMessage(msg);
                    }
                }

            }
        })).start();
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

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(download_qcm), R.drawable.ic_action_download, Color.GRAY);
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
                        status = saveQcm();
                        if (ErrorStatus.NO_ERROR != status) {
                            Log.e(TAG, getString(R.string.error_save) + status);
                            Toast.makeText(mContext, getString(R.string.error_save), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mContext, R.string.saved, Toast.LENGTH_LONG).show();
                        }
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
        assert list != null;
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                    break;
                }
            }
            this.qcm.setFamilleQuestionnaire(familleQuestionnaires);
            QcmStorageManager.saveQcm(this, this.qcm);
        }
    }

    public TextView getTvMessageSystem() {
        if (tvMessageSystem == null) {
            tvMessageSystem = (TextView) findViewById(R.id.tvMainActivity_MessageSystem);
        }
        return tvMessageSystem;
    }

    enum ErrorStatus {
        NO_ERROR, ERROR_DOWNLOAD, ERROR_SAVE
    }
}
