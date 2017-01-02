package goudard.david.qcm.adapter;

import goudard.david.qcm.entity.Survey;

/**
 * The SurverAdapter Interface
 *
 * @author David GOUDARD
 * @version 1
 * @since 23/12/2016
 */
public interface SurveyAdapterListenerInterface {

    /**
     * Event onClick
     *
     * @param item     Survey
     * @param position int
     */
    void onClickSurvey(Survey item, int position);
}