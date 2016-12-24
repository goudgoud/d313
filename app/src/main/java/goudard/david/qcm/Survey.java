package goudard.david.qcm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by david on 19/12/16.
 */

public class Survey implements Serializable {

    private String code;
    private String name;
    private ArrayList<Question> questions;
    private int score = 0;
    private int questionInProgress = -1;

    /**
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code String
     * @return Survey
     */
    public Survey setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name String
     * @return Survey
     */
    public Survey setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public Survey setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Survey addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
        return this;
    }

    public int getScore() {
        return this.score;
    }

    public Survey setScore(int score) {
        this.score = score;
        return this;
    }

    public int getQuestionInProgress() {
        return questionInProgress;
    }

    public Survey setQuestionInProgress(int questionInProgress) {
        this.questionInProgress = questionInProgress;
        return this;
    }

    public Survey reset() {
        this.score = 0;
        this.questionInProgress = -1;
        Iterator<Question> iterator = this.questions.iterator();
        ArrayList<Question> newQuestions = new ArrayList<>();

        while (iterator.hasNext()) {
            Question question = iterator.next();
            question.setResponse(-1);
            newQuestions.add(question);
        }
        this.setQuestions(newQuestions);

        return this;
    }
}
