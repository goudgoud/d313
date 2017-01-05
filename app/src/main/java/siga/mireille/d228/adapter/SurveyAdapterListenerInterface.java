package siga.mireille.d228.adapter;

import siga.mireille.d228.entity.Survey;

/**
 * The SurverAdapter Interface
 *
 * @author SIGA Mireille
 * @version 1
 * @since 23 /12/2016
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