package goudard.david.qcm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by david on 19/12/16.
 */

public class Question implements Serializable {

    private String titre;
    private ArrayList<String> choix;
    private int correct;
    private int response = -1;

    public String getTitre() {
        return this.titre;
    }

    public Question setTitre(String titre) {
        this.titre = titre;
        return this;
    }

    public ArrayList<String> getChoix() {
        return choix;
    }

    public Question setChoix(ArrayList<String> choix) {
        this.choix = choix;
        return this;
    }

    public int getCorrect() {
        return this.correct;
    }

    public Question setCorrect(int correct) {
        this.correct = correct;
        return this;
    }

    public int getResponse() {
        return this.response;
    }

    public Question setResponse(int response) {
        this.response = response;
        return this;
    }


}
