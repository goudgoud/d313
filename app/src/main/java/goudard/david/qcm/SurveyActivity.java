package goudard.david.qcm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by david on 24/12/16.
 */

public class SurveyActivity extends AppCompatActivity {

    private Survey survey;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.survey = (Survey) getIntent().getSerializableExtra(SurveyFamilyActivity.KEY_FROM_SURVEY_FAMILY);

        setContentView(R.layout.activity_survey);
    }
}
