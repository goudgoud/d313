package goudard.david.qcm.adapter;

import goudard.david.qcm.entity.Survey;

/**
 * Created by david on 23/12/16.
 */

public interface SurveyAdapterListenerInterface {
    public void onClickSurvey(Survey item, int position);
}