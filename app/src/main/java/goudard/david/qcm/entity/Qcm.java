package goudard.david.qcm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by david on 19/12/16.
 */

public class Qcm implements Serializable {

    private ArrayList<SurveyFamily> familleQuestionnaires;

    public ArrayList<SurveyFamily> getFamilleQuestionnaire() {
        return this.familleQuestionnaires;
    }

    public Qcm setFamilleQuestionnaire(ArrayList<SurveyFamily> familleQuestionnaire) {
        this.familleQuestionnaires = familleQuestionnaire;
        return this;
    }

    public Qcm addFamilleQuestionnaire(SurveyFamily surveyFamily) {
        if (familleQuestionnaires == null) {
            familleQuestionnaires = new ArrayList<>();
        }
        familleQuestionnaires.add(surveyFamily);
        return this;
    }
}
