package goudard.david.qcm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import goudard.david.qcm.adapter.AnswerAdapter;
import goudard.david.qcm.entity.Question;
import goudard.david.qcm.adapter.QuestionAdapter;
import goudard.david.qcm.adapter.QuestionAdapterListenerInterface;
import goudard.david.qcm.R;
import goudard.david.qcm.manager.SerializableManager;
import goudard.david.qcm.entity.Survey;

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

    protected void onStop() {
        super.onStop();
        SerializableManager.saveSerializable(this, this.survey, "survey.er");
    }

    protected void onRestart() {
        super.onRestart();
        try {
            Survey survey = (Survey) SerializableManager.readSerializable(this, "survey.er");
            if (survey != null) this.survey = survey;
            SerializableManager.removeSerializable(this,"survey.er");
        }
        catch (Exception e) {

        }
    }

    private void showQuestion() {
        //Création et initialisation de l'Adapter
        //Récupération de la liste
        int questionToShow  = this.survey.getQuestionInProgress();
        QuestionAdapter adapter = new QuestionAdapter(this, getListChoixQuestion(questionToShow));
        //Ecoute des évènements sur la liste
        adapter.addListener(this);
        initView(adapter, questionToShow);
        adapter.notifyDataSetChanged();
    }

    private void showAnswer() {
        Button button = (Button) findViewById(R.id.btnSurveyActivity_ButtonNext);
        int questionToShow = this.survey.getQuestionInProgress() - 1;
        AnswerAdapter adapter = new AnswerAdapter(this, getListChoixQuestion(questionToShow));
        if (questionToShow + 1 >= this.survey.getQuestions().size()) {
            button.setText(R.string.Finish);
        }
        button.setVisibility(View.VISIBLE);
        initView(adapter, questionToShow);
        adapter.notifyDataSetChanged();
    }

    private void initView(ListAdapter adapter, int questionToShow) {
        TextView tv = (TextView) findViewById(R.id.tvSurveyActivity_Question_Title);
        //int questionInProgress = survey.getQuestionInProgress();
        ArrayList<Question> questions = this.survey.getQuestions();
        Question question = questions.get(questionToShow);
        tv.setText(question.getTitre());

        TextView tvSystem = (TextView) findViewById(R.id.tvSurveyActivity_MessageSystem);
        String msg = new Integer(questionToShow + 1).toString()
                + "/"
                + new Integer(questions.size()).toString();
        tvSystem.setText(msg);

        //Récupération du composant ListView
        ListView list = (ListView) findViewById(R.id.lvSurveyActivity_Question);

        //Initialisation de la liste avec les données
        list.setAdapter(adapter);

    }

    private ArrayList<String> getListChoixQuestion(int questionToShow) {
        //Récupération de la liste
        ArrayList<Question> questions = this.survey.getQuestions();
        Question question = questions.get(questionToShow);
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
        questionInProgress++;
        this.survey.setQuestionInProgress(questionInProgress);
        showAnswer();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(KEY_FROM_SURVEY, (Serializable) this.survey);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    public void onClickNext(View v) {
        int question = this.survey.getQuestionInProgress();
        if (question + 1 > this.survey.getQuestions().size()) {
            this.finish();
        }
        else {
            Button button = (Button) findViewById(R.id.btnSurveyActivity_ButtonNext);
            button.setVisibility(View.INVISIBLE);
            showQuestion();
        }
    }
 }
