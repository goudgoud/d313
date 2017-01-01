package goudard.david.qcm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import goudard.david.qcm.adapter.AnswerAdapter;
import goudard.david.qcm.entity.Question;
import goudard.david.qcm.adapter.QuestionAdapter;
import goudard.david.qcm.adapter.QuestionAdapterListenerInterface;
import goudard.david.qcm.R;
import goudard.david.qcm.manager.SerializableManager;
import goudard.david.qcm.entity.Survey;
import goudard.david.qcm.tools.ElapsedTime;

/**
 * Created by david on 24/12/16.
 */

public class SurveyActivity extends AppCompatActivity implements QuestionAdapterListenerInterface {
    public static final String KEY_FROM_SURVEY = "KEY_FROM_SURVEY";
    private Survey survey;
    private long startQuestion;
    private ElapsedTime elapsedTime;

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
        elapsedTime = new ElapsedTime(this);
        showQuestion();
    }

    protected void onStop() {
        super.onStop();
        SerializableManager.saveSerializable(this, this.survey, "survey.er");
    }

    protected void onRestart() {
        super.onRestart();
        try {
            Survey survey = SerializableManager.readSerializable(this, "survey.er");
            if (survey != null) this.survey = survey;
            SerializableManager.removeSerializable(this,"survey.er");
        }
        catch (Exception ignored) {

        }
    }

    private void showQuestion() {
        //Création et initialisation de l'Adapter
        //Récupération de la liste
        int questionToShow  = this.survey.getQuestionInProgress();
        QuestionAdapter adapter = new QuestionAdapter(this, this.survey.getQuestions().get(questionToShow));
        //Ecoute des évènements sur la liste
        adapter.addListener(this);
        initView(adapter, questionToShow);
        adapter.notifyDataSetChanged();
        elapsedTime.start();
    }

    private void showAnswer() {
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.btnSurveyActivity_ButtonNext);
        int questionToShow = this.survey.getQuestionInProgress() - 1;
        AnswerAdapter adapter = new AnswerAdapter(this, this.survey.getQuestions().get(questionToShow));
        if (questionToShow + 1 >= this.survey.getQuestions().size()) {
            assert button != null;
            button.setImageResource(R.drawable.ic_action_exit_survey);
        }
        assert button != null;
        button.setVisibility(View.VISIBLE);
        initView(adapter, questionToShow);

    }

    private void initView(QuestionAdapter adapter, int questionToShow) {
        TextView tv = (TextView) findViewById(R.id.tvSurveyActivity_Question_Title);
        //int questionInProgress = survey.getQuestionInProgress();
        ArrayList<Question> questions = this.survey.getQuestions();
        Question question = questions.get(questionToShow);
        assert tv != null;
        tv.setText(question.getTitre());

        TextView tvSystem = (TextView) findViewById(R.id.tvSurveyActivity_MessageSystem);
        String msg = Integer.toString(questionToShow + 1)
                + "/"
                + Integer.toString(questions.size());
        assert tvSystem != null;
        tvSystem.setText(msg);

        //Récupération du composant ListView
        ListView list = (ListView) findViewById(R.id.lvSurveyActivity_Question);

        //Initialisation de la liste avec les données
        assert list != null;
        list.setAdapter(null);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private ArrayList<String> getListChoixQuestion(int questionToShow) {
        //Récupération de la liste
        ArrayList<Question> questions = this.survey.getQuestions();
        Question question = questions.get(questionToShow);
        return question.getChoix();
    }

    @Override
    public void onClickQuestion(String item, int position) {
        // temps écoulé pour la question
        elapsedTime.stop();
        this.survey.addTimeElapsed(elapsedTime.getElapsedTimeSecs());

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
        intent.putExtra(KEY_FROM_SURVEY, this.survey);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    public void onClickNext(View v) {
        int question = this.survey.getQuestionInProgress();
        if (question + 1 > this.survey.getQuestions().size()) {
            showResult();
            this.finish();
        }
        else {
            FloatingActionButton button = (FloatingActionButton) findViewById(R.id.btnSurveyActivity_ButtonNext);
            assert button != null;
            button.setVisibility(View.INVISIBLE);
            showQuestion();
        }
    }

    private void showResult() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.result_popupview);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvResultSurvey_Title);
        TextView tvScore = (TextView) dialog.findViewById(R.id.tvResultSurvey_Score);
        TextView tvTime  = (TextView) dialog.findViewById(R.id.tvResultSurvey_Time);

        int score = this.survey.getScore();
        int nbQuestions =  this.survey.getQuestions().size();

        //assert tvTitle != null;
        if (score == nbQuestions) {
            tvTitle.setText(getString((R.string.congratulations)));
        }
        else if ( score > (int) (nbQuestions / 2)) {

            tvTitle.setText(getString(R.string.good_results));
        }
        else {
            tvTitle.setText(getString(R.string.test_result));
        }
        dialog.setTitle(tvTitle.getText());

        assert tvScore != null;
        tvScore.setText(getString(R.string.your_score) + " : " + Integer.toString(score) + "/" + Integer.toString(nbQuestions));

        assert tvTime != null;
        tvTime.setText(getString(R.string.time_elapsed) + " : " + ElapsedTime.getStringElapsedTime(this.survey.getTimeElapsed(), this));

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
