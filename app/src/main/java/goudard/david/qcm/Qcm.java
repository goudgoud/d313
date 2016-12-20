package goudard.david.qcm;

import java.util.ArrayList;

/**
 * Created by david on 19/12/16.
 */

public class Qcm {

    private ArrayList<SurveyFamily> familleQuestionnaire;

    public ArrayList<SurveyFamily> getFamilleQuestionnaire() {
        return this.familleQuestionnaire;
    }

    public Qcm setFamilleQuestionnaire(ArrayList<SurveyFamily> familleQuestionnaire) {
        this.familleQuestionnaire = familleQuestionnaire;
        return this;
    }

    public Qcm addFamilleQuestionnaire(SurveyFamily surveyFamily) {
        familleQuestionnaire.add(surveyFamily);
        return this;
    }
}
