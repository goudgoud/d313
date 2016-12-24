package goudard.david.qcm;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by david on 23/12/16.
 */

public class SurveyFamilyActivity extends AppCompatActivity implements SurveyAdapterListenerInterface {

    public static final String KEY_FROM_SURVEY_FAMILY = "KEY_FROM_SURVEY_FAMILY";
    public static final int RQC_SURVEY = 3001;
    private SurveyFamily surveyFamily;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.surveyFamily = (SurveyFamily) getIntent().getSerializableExtra(MainActivity.KEY_FROM_MAIN);

        setContentView(R.layout.activity_survey_family);
        this.initListView();
    }

    private void initListView() {
        //Récupération de la liste des personnes
        ArrayList<Survey> listSurvey = this.surveyFamily.getQuestionnaire();

        //Création et initialisation de l'Adapter pour les personnes
        SurveyAdapter adapter = new SurveyAdapter(this, listSurvey);

        //Ecoute des évènements sur la liste
        adapter.addListener(this);

        //Récupération du composant ListView
        ListView list = (ListView) findViewById(R.id.lvSurveyFamilyActivity_Survey);

        //Initialisation de la liste avec les données
        list.setAdapter(adapter);
    }

    @Override
    public void onClickSurvey(Survey item, int position) {

        // Si preférence est de pouvoir refaire un questionnaire et si questionnaire commencé,
        // alors afficher demander si continuer ou recommencer questionnaire
        surveyRestartDialog(item);
    }

    private void surveyRestartDialog(final Survey survey) {
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
        //On crée un bouton "Recommencer" à notre AlertDialog et on lui affecte un évènement
        adb.setNegativeButton(R.string.resume, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                launchSurvey(survey);
            }
        });
        adb.show();
    }

    private void launchSurvey(Survey survey) {
        Intent myIntent = new Intent(SurveyFamilyActivity.this, SurveyActivity.class);
        myIntent.putExtra(KEY_FROM_SURVEY_FAMILY, survey);
        startActivityForResult(myIntent, RQC_SURVEY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Resources res = getResources();
    }

}
