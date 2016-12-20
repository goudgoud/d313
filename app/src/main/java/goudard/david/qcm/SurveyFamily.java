package goudard.david.qcm;

import java.util.ArrayList;

/**
 * Created by david on 19/12/16.
 */

public class SurveyFamily {
    private String name;
    private ArrayList<Survey> questionnaire;

    public String getName() {
        return this.name;
    }

    public SurveyFamily setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Survey> getQuestionnaire() {
        return this.questionnaire;
    }

    public SurveyFamily setQuestionnaire(ArrayList<Survey> questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public SurveyFamily addQuestionnaire(Survey survey) {
        questionnaire.add(survey);
        return this;
    }
}
