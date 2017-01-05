package siga.mireille.d228.adapter;

import siga.mireille.d228.entity.SurveyFamily;

/**
 * SurveyFamilyAdapter Interface
 *
 * @author SIGA Mireille
 * @version 1
 * @since 23 /12/2016
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