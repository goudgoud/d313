package goudard.david.qcm.adapter;

/**
 * Listener interface of QuestionAdapter
 *
 * @author David GOUDARD
 * @version 1
 * @since 24/12/2016
 */
public interface QuestionAdapterListenerInterface {

    /**
     * Event onclick
     *
     * @param item     String choice of response
     * @param position int
     */
    public void onClickQuestion(String item, int position);
}