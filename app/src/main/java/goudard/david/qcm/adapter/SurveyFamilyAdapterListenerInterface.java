package goudard.david.qcm.adapter;

import goudard.david.qcm.entity.SurveyFamily;

/**
 * SurveyFamilyAdapter Interface
 *
 * @author David GOUDARD
 * @version 1
 * @since 23/12/2016
 */
public interface SurveyFamilyAdapterListenerInterface {

    /**
     * Event onClick
     *
     * @param item     SurveyFamily
     * @param position int
     */
    void onClickSurveyFamily(SurveyFamily item, int position);
}