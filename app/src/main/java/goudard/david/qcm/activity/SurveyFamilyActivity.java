package goudard.david.qcm.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import goudard.david.qcm.R;
import goudard.david.qcm.entity.Survey;
import goudard.david.qcm.adapter.SurveyAdapter;
import goudard.david.qcm.adapter.SurveyAdapterListenerInterface;
import goudard.david.qcm.entity.SurveyFamily;

import static goudard.david.qcm.activity.SurveyActivity.KEY_FROM_SURVEY;

/**
 * Survey family activity
 * Show the differents available surveys
 *
 * @author David Goudard
 * @version 1
 */
public class SurveyFamilyActivity extends AppCompatActivity implements SurveyAdapterListenerInterface {

    /**
     * The constant KEY_FROM_SURVEY_FAMILY.
     */
    public static final String KEY_FROM_SURVEY_FAMILY = "KEY_FROM_SURVEY_FAMILY";
    /**
     * The constant RQC_SURVEY.
     */
    public static final int RQC_SURVEY = 3001;
    private SurveyFamily surveyFamily;
    private SharedPreferences sharedPreferences;
    private SurveyAdapter surveyAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.surveyFamily = (SurveyFamily) getIntent().getSerializableExtra(MainActivity.KEY_FROM_MAIN);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        setContentView(R.layout.activity_survey_family);
        initListView();
    }

    protected void onRestart() {
        super.onRestart();
        surveyAdapter.notifyDataSetChanged();
    }

    /**
     * Init the listview of available surveys.
     * show name, score and progress
     */
    private void initListView() {
        //Récupération de la liste des personnes
        ArrayList<Survey> listSurvey = this.surveyFamily.getQuestionnaire();

        //Création et initialisation de l'Adapter pour les personnes
        surveyAdapter = new SurveyAdapter(this, listSurvey);
        //Ecoute des évènements sur la liste
        surveyAdapter.addListener(this);

        //Récupération du composant ListView
        ListView list = (ListView) findViewById(R.id.lvSurveyFamilyActivity_Survey);
        //Initialisation de la liste avec les données
        assert list != null;
        list.setAdapter(surveyAdapter);
    }

    /**
     * When a survey is clicked.
     * Depending on preferences, the survey can be restart or just resume
     *
     * @param item     Item, the survey selected
     * @param position int, position in listview
     */
    @Override
    public void onClickSurvey(Survey item, int position) {
        final boolean bCanRestart;

        // Si preférence est de pouvoir refaire un questionnaire et si questionnaire commencé,
        // alors afficher demander si continuer ou recommencer questionnaire
        bCanRestart = this.sharedPreferences.getBoolean("pref_qcm_restart", true);

        if (item.getQuestionInProgress() + 1 > item.getQuestions().size()) {
            if (bCanRestart) {
                surveyRestartDialog(item);
            } else {
                Toast.makeText(this, R.string.already_completed, Toast.LENGTH_SHORT).show();
            }
        } else if (item.getQuestionInProgress() > 0) {
            if (bCanRestart) {
                surveyRestartResumeDialog(item);
            } else {
                launchSurvey(item);
            }
        } else {
            item.reset();
            launchSurvey(item);
        }
    }

    /**
     * Create dialog to ask restart survey
     *
     * @param item Survey to restart
     */
    private void surveyRestartDialog(final Survey item) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.restartSurveyTitle)
                .setMessage(R.string.restartSurvey)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        item.reset();
                        launchSurvey(item);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Create dialog to choose between resume or restart
     *
     * @param survey Survey to resume or restart
     */
    private void surveyRestartResumeDialog(final Survey survey) {
        final boolean[] response = new boolean[1];

        // instancie layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.alert_dialog_survey_restart, null);

        //Création de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);
        //On donne un titre à l'AlertDialog
        adb.setTitle(R.string.survey_restart_title);
        //On modifie l'icône de l'AlertDialog pour le fun ;)
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        //On affecte un bouton "Recommencer" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                survey.reset();
                launchSurvey(survey);
            }
        });
        //On crée un bouton "Continuer" à notre AlertDialog et on lui affecte un évèthis.initListView();nement
        adb.setNegativeButton(R.string.resume, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                launchSurvey(survey);
            }
        });
        adb.show();
    }

    /**
     * Launch the selected survey
     *
     * @param survey Survey
     */
    private void launchSurvey(Survey survey) {
        Intent myIntent = new Intent(SurveyFamilyActivity.this, SurveyActivity.class);
        myIntent.putExtra(KEY_FROM_SURVEY_FAMILY, survey);
        startActivityForResult(myIntent, RQC_SURVEY);
    }

    /**
     * Return of Survey Activity
     *
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Resources res = getResources();
        if (requestCode == RQC_SURVEY && resultCode == RESULT_OK) {
            Survey survey = (Survey) data.getSerializableExtra(KEY_FROM_SURVEY);
            // store survey updated    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            ArrayList<Survey> questionnaires = this.surveyFamily.getQuestionnaire();
            Iterator iterator = questionnaires.iterator();
            int idx = -1;
            while (iterator.hasNext()) {
                idx++;
                if (Objects.equals(questionnaires.get(idx).getCode(), survey.getCode())) {
                    questionnaires.set(idx, survey);
                    this.surveyFamily.setQuestionnaire(questionnaires);
                    break;
                }
            }
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(KEY_FROM_SURVEY_FAMILY, this.surveyFamily);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
