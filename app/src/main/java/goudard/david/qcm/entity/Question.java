package goudard.david.qcm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Question class
 *
 * @author David GOUDARD
 * @version 1
 * @since 19 /12/2016
 */
public class Question implements Serializable {

    /**
     * Title of question
     */
    private String titre;

    /**
     * List of choice
     */
    private ArrayList<String> choix;

    /**
     * The correct response
     */
    private int correct;

    /**
     * The user response
     */
    private int response = -1;

    /**
     * Title getter
     *
     * @return String titre
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * Title setter
     *
     * @param titre String
     * @return Question titre
     */
    public Question setTitre(String titre) {
        this.titre = titre;
        return this;
    }

    /**
     * List of response choice getter
     *
     * @return ArrayList<String>    choix
     */
    public ArrayList<String> getChoix() {
        return choix;
    }

    /**
     * List of choice setter
     *
     * @param choix ArrayList<String>
     * @return Question choix
     */
    public Question setChoix(ArrayList<String> choix) {
        this.choix = choix;
        return this;
    }

    /**
     * Correct response getter
     *
     * @return int correct
     */
    public int getCorrect() {
        return this.correct;
    }

    /**
     * Correct response setter
     *
     * @param correct int number of good response
     * @return Question correct
     */
    public Question setCorrect(int correct) {
        this.correct = correct;
        return this;
    }

    /**
     * User response getter
     *
     * @return int response
     */
    public int getResponse() {
        return this.response;
    }

    /**
     * Use response setter
     *
     * @param response int
     * @return Question response
     */
    public Question setResponse(int response) {
        this.response = response;
        return this;
    }
}
