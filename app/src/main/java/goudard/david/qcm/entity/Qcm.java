package goudard.david.qcm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Multiple Choice Tests class
 *
 * @author David GOUDARD
 * @version 1
 * @since 19/12/2016
 */
public class Qcm implements Serializable {

    /**
     * List of Survey families
     */
    private ArrayList<SurveyFamily> familleQuestionnaires = new ArrayList<>();

    /**
     * Survey families getter
     *
     * @return ArrayList<SurveyFamily>
     */
    public ArrayList<SurveyFamily> getFamilleQuestionnaire() {
        return this.familleQuestionnaires;
    }

    /**
     * Survey families setter
     *
     * @param familleQuestionnaire ArrayList<SurveyFamily> list of survey families
     * @return Qcm
     */
    public Qcm setFamilleQuestionnaire(ArrayList<SurveyFamily> familleQuestionnaire) {
        this.familleQuestionnaires = familleQuestionnaire;
        return this;
    }

    /**
     * Add a survey family
     *
     * @param surveyFamily SurveyFamily
     * @return Qcm
     */
    public Qcm addFamilleQuestionnaire(SurveyFamily surveyFamily) {
        if (familleQuestionnaires == null) {
            familleQuestionnaires = new ArrayList<>();
        }
        familleQuestionnaires.add(surveyFamily);
        return this;
    }
}
