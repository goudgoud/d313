package goudard.david.qcm;

import java.util.ArrayList;

/**
 * Created by david on 19/12/16.
 */

public class Survey {

    private String code;
    private String name;
    private ArrayList<Question> questions;

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
        questions.add(question);
        return this;
    }
}
