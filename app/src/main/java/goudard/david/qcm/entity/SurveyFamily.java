package goudard.david.qcm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Survey family class
 *
 * @author David GOUDARD
 * @version 1
 * @since 19/12/2016
 */
public class SurveyFamily implements Serializable {

    /**
     * The name of survey family
     */
    private String name;

    /**
     * List of surveys
     */
    private ArrayList<Survey> questionnaires;

    /**
     * Name getter
     *
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Name setter
     *
     * @param name String
     * @return SurveyFamily
     */
    public SurveyFamily setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * List of surveys getter
     *
     * @return ArrayList<Survey>
     */
    public ArrayList<Survey> getQuestionnaire() {
        return this.questionnaires;
    }

    /**
     * Surveys list setter
     *
     * @param questionnaire ArrayList<Survey>
     * @return SurveyFamily
     */
    public SurveyFamily setQuestionnaire(ArrayList<Survey> questionnaire) {
        this.questionnaires = questionnaire;
        return this;
    }

    /**
     * Add a survey
     *
     * @param survey Survey
     * @return SurveyFamily
     */
    public SurveyFamily addQuestionnaire(Survey survey) {
        if (questionnaires == null) {
            questionnaires = new ArrayList<>();
        }
        questionnaires.add(survey);
        return this;
    }
}
