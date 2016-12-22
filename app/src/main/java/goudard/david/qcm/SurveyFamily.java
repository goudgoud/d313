package goudard.david.qcm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by david on 19/12/16.
 */

public class SurveyFamily implements Serializable {
    private String name;
    private ArrayList<Survey> questionnaires;

    public String getName() {
        return this.name;
    }

    public SurveyFamily setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Survey> getQuestionnaire() {
        return this.questionnaires;
    }

    public SurveyFamily setQuestionnaire(ArrayList<Survey> questionnaire) {
        this.questionnaires = questionnaire;
        return this;
    }

    public SurveyFamily addQuestionnaire(Survey survey) {
        if (questionnaires == null) {
            questionnaires = new ArrayList<>();
        }
        questionnaires.add(survey);
        return this;
    }
}
