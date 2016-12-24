package goudard.david.qcm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by david on 23/12/16.
 */

public class SurveyFamilyActivity extends AppCompatActivity implements SurveyAdapterListenerInterface {

    private SurveyFamily surveyFamily;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.surveyFamily = (SurveyFamily) getIntent().getSerializableExtra(MainActivity.KEY_FROM_MAIN);

        setContentView(R.layout.activity_survey_family);
    }

    private void initListView_Qcm() {
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

    }
}
