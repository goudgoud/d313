package goudard.david.qcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import static goudard.david.qcm.SurveyFamilyActivity.RQC_SURVEY;

/**
 * Created by david on 24/12/16.
 */

public class SurveyActivity extends AppCompatActivity implements QuestionAdapterListenerInterface {
    public static final String KEY_FROM_SURVEY = "KEY_FROM_SURVEY";
    private Survey survey;

    public Survey getSurvey() {
        return this.survey;
    }

    public SurveyActivity setSurvey(Survey survey) {
        this.survey = survey;
        return this;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.survey = (Survey) getIntent().getSerializableExtra(SurveyFamilyActivity.KEY_FROM_SURVEY_FAMILY);

        setContentView(R.layout.activity_survey);
    }

    protected void onStart() {
        super.onStart();
        showQuestion();
    }

    protected void onPause() {
        super.onPause();
        SerializableManager.saveSerializable(this, this.survey, "survey.er");
    }

    protected void onResume() {
        super.onResume();
        try {
            Survey survey = (Survey) SerializableManager.readSerializable(this, "survey.er");
            this.survey = survey;
            SerializableManager.removeSerializable(this,"survey.er");
        }
        catch (Exception e) {

        }
    }

    private void showQuestion() {
        //Création et initialisation de l'Adapter
        //Récupération de la liste
        QuestionAdapter adapter = new QuestionAdapter(this, getListChoixQuestion());
        //Ecoute des évènements sur la liste
        adapter.addListener(this);
        initView(adapter);

    }

    private void showAnswer() {
        AnswerAdapter adapter = new AnswerAdapter(this, getListChoixQuestion());
        initView(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initView(ListAdapter adapter) {

        TextView tv = (TextView) findViewById(R.id.tvSurveyActivity_Question_Title);
        int questionInProgress = survey.getQuestionInProgress();
        ArrayList<Question> questions = this.survey.getQuestions();
        Question question = questions.get(questionInProgress);
        tv.setText(question.getTitre());

        TextView tvSystem = (TextView) findViewById(R.id.tvSurveyActivity_MessageSystem);
        String msg = new Integer(questionInProgress + 1).toString()
                + "/"
                + new Integer(questions.size() + 1).toString();
        tvSystem.setText(msg);

        //Récupération du composant ListView
        ListView list = (ListView) findViewById(R.id.lvSurveyActivity_Question);

        //Initialisation de la liste avec les données
        list.setAdapter(adapter);

    }

    private ArrayList<String> getListChoixQuestion() {
        //Récupération de la liste
        int questionInProgress = survey.getQuestionInProgress();
        ArrayList<Question> questions = this.survey.getQuestions();
        Question question = questions.get(questionInProgress);
        return question.getChoix();
    }

    @Override
    public void onClickQuestion(String item, int position) {
        int questionInProgress = this.survey.getQuestionInProgress();
        ArrayList<Question> questions = survey.getQuestions();
        Question question = questions.get(questionInProgress);
        if (position == question.getCorrect()) {
            this.survey.setScore(survey.getScore() + 1);
        }
        question.setResponse(position);
        questions.set(questionInProgress, question);
        this.survey.setQuestions(questions);
        showAnswer();

        questionInProgress++;
        this.survey.setQuestionInProgress(questionInProgress);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(KEY_FROM_SURVEY, (Serializable) this.survey);
        setResult(RESULT_OK, intent);
        super.finish();
    }

}
