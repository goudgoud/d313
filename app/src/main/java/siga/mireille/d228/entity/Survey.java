package siga.mireille.d228.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Survey class
 *
 * @author SIGA Mireille
 * @version 1
 * @since 19 /12/2016
 */
public class Survey implements Serializable {

    /**
     * Code of survey
     */
    private String code;

    /**
     * Name of surveyr
     */
    private String name;

    /**
     * List of questions
     */
    private ArrayList<Question> questions;

    /**
     * User score
     */
    private int score = 0;

    /**
     * Question in progress
     */
    private int questionInProgress = 0;

    /**
     * Elapsed time to answer
     */
    private long timeElapsed = 0;

    /**
     * Code getter
     *
     * @return String code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Code setter
     *
     * @param code String
     * @return Survey code
     */
    public Survey setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * Name getter
     *
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Name setter
     *
     * @param name String
     * @return Survey name
     */
    public Survey setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * List of questions getter
     *
     * @return ArrayList<Question>    questions
     */
    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    /**
     * List of questions setter
     *
     * @param questions ArrayList<Question>
     * @return Survey questions
     */
    public Survey setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
        return this;
    }

    /**
     * Add a question
     *
     * @param question Question to add
     * @return Survey survey
     */
    public Survey addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
        return this;
    }

    /**
     * Score getter
     *
     * @return int score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Score getter
     *
     * @param score int
     * @return Survey score
     */
    public Survey setScore(int score) {
        this.score = score;
        return this;
    }

    /**
     * Question number in progress getter
     *
     * @return int question in progress
     */
    public int getQuestionInProgress() {
        return questionInProgress;
    }

    /**
     * Question number in progress setter
     *
     * @param questionInProgress int
     * @return Survey question in progress
     */
    public Survey setQuestionInProgress(int questionInProgress) {
        this.questionInProgress = questionInProgress;
        if (questionInProgress >= questions.size()) {
            this.questionInProgress = questions.size();
        } else {
            this.questionInProgress = questionInProgress;
        }
        return this;
    }

    /**
     * Reset the survey
     * Score and questionInProgress are set to 0
     *
     * @return Survey survey
     */
    public Survey reset() {
        this.score = 0;
        this.questionInProgress = 0;
        Iterator<Question> iterator = this.questions.iterator();

        int count = 0;
        while (iterator.hasNext()) {
            Question question = iterator.next();
            question.setResponse(-1);
            questions.set(count, question);
            count++;
        }
        return this;
    }

    /**
     * Elapsed time in second getter
     *
     * @return long time elapsed
     */
    public long getTimeElapsed() {
        return this.timeElapsed;
    }

    /**
     * Elapsed time setter
     *
     * @param timeElapsed long in seconds
     * @return Survey time elapsed
     */
    public Survey setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
        return this;
    }

    /**
     * Add second(s) to elapsed time
     *
     * @param time long
     * @return Survey survey
     */
    public Survey addTimeElapsed(long time) {
        this.timeElapsed += time;
        return this;
    }
}
