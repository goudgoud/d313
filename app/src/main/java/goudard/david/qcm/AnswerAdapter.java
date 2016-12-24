package goudard.david.qcm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 24/12/16.
 */

public class AnswerAdapter extends QuestionAdapter {

    public AnswerAdapter(Context context, List<String> aListChoix) {
        super(context, aListChoix);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.activity_survey_listview_question, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView du layout
        TextView tvQuestionChoix = (TextView) layoutItem.findViewById(R.id.lvSurvey_Question_tvText);

        //(3) : Renseignement des valeurs
        tvQuestionChoix.setText(mListChoix.get(position));

        // positionne réponse
        SurveyActivity surveyActivity = (SurveyActivity) mContext;
        ImageView imageView = (ImageView) layoutItem.findViewById(R.id.lvQuestion_img);
        int questionInProgress = surveyActivity.getSurvey().getQuestionInProgress();
        Question question = surveyActivity.getSurvey().getQuestions().get(questionInProgress);
        if (position == question.getResponse()) {
            if (position == question.getCorrect()) {
                imageView.setImageResource(R.drawable.ic_ok);
            } else {
                imageView.setImageResource(R.drawable.ic_no);
            }
        }

        //On mémorise la position dans le composant textview
        tvQuestionChoix.setTag(position);

        //On retourne l'item créé.
        return layoutItem;
    }

}