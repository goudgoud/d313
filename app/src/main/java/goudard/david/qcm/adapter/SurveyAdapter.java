package goudard.david.qcm.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import goudard.david.qcm.R;
import goudard.david.qcm.entity.Survey;

/**
 * Created by david on 23/12/16.
 */

public class SurveyAdapter extends BaseAdapter {

    // Une liste de questionnaires
    private List<Survey> mListSurvey;

    //Le contexte dans lequel est présent l' adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    //Contient la liste des listeners
    private ArrayList<SurveyAdapterListenerInterface> mListListener = new ArrayList<SurveyAdapterListenerInterface>();


    public SurveyAdapter(Context context, List<Survey> aListSurvey) {
        this.mContext = context;
        this.mListSurvey = aListSurvey;
        this.mInflater = LayoutInflater.from(mContext);
    }

    // Comptage des items
    public int getCount() {
        return mListSurvey.size();
    }

    // Identification des items
    // par position
    public Object getItem(int position) {
        return mListSurvey.get(position);
    }

    // par id
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.activity_survey_family_listview_survey, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        Survey survey = mListSurvey.get(position);

        // si questionnaire terminé, et que l'on ne peut le refaire
        boolean bCanRestart;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        bCanRestart = sharedPreferences.getBoolean("pref_qcm_restart", true);

        if (survey.getQuestionInProgress() >= survey.getQuestions().size()) {
            if (!bCanRestart) {
                layoutItem.setEnabled(false);
            }
        }

        //(2) : Récupération des TextView du layout
        TextView tvSurvey = (TextView) layoutItem.findViewById(R.id.lvSurveyFamily_Survey_tvText);
        TextView tvSurveyScore  = (TextView) layoutItem.findViewById(R.id.tvllSurveyFamily_Survey_Score);
        TextView tvSurveyProgress = (TextView) layoutItem.findViewById(R.id.tvllSurveyFamily_Survey_Progress);

        //(3) : Renseignement des valeurs
        tvSurvey.setText(survey.getName());
        tvSurveyScore.setText(Integer.toString(survey.getScore()));

        tvSurveyProgress.setText(
                Integer.toString(
                        (int)(100*survey.getQuestionInProgress()/survey.getQuestions().size())
                ) + "%"
        );

        //On mémorise la position
        layoutItem.setTag(position);
        //On ajoute un listener
        layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Survey"
                Integer position = (Integer) v.getTag();
                //On prévient les listeners qu'il y a eu un clic sur le TextView
                sendListener(mListSurvey.get(position), position);
            }
        });

        //On retourne l'item créé.
        return layoutItem;
    }


    /***************************
     * Listener
     */

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(SurveyAdapterListenerInterface aListener) {
        mListListener.add(aListener);
    }

    private void sendListener(Survey item, int position) {
        for (int i = mListListener.size() - 1; i >= 0; i--) {
            mListListener.get(i).onClickSurvey(item, position);
        }
    }
}
