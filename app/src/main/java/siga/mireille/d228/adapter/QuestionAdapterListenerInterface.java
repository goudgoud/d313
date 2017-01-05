package siga.mireille.d228.adapter;

/**
 * Listener interface of QuestionAdapter
 *
 * @author SIGA Mireille
 * @version 1
 * @since 24 /12/2016
 */
public interface QuestionAdapterListenerInterface {

    /**
     * Event onclick
     *
     * @param item     String choice of response
     * @param position int
     */
    void onClickQuestion(String item, int position);
}