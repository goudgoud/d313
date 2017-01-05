package siga.mireille.d228.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import siga.mireille.d228.entity.Question;
import siga.mireille.d228.R;

import static siga.mireille.d228.R.color;

/**
 * Answer adapter
 * <p>
 * Use to show user response and if necessary the good response
 *
 * @author SIGA Mireille
 * @version 1
 * @see siga.mireille.d228.adapter.QuestionAdapter
 * @since 24 /12/2016
 */
public class AnswerAdapter extends QuestionAdapter {

    /**
     * Instantiates a new Answer adapter.
     *
     * @param context  the context
     * @param question the question
     */
    public AnswerAdapter(Context context, Question question) {
        super(context, question);
    }

    /**
     * Show line of ListView
     *
     * @param position    number in listview
     * @param convertView View used
     * @param parent      ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layoutItem;

        //(1) : Réutilisation des layouts
        layoutItem = (LinearLayout) mInflater.inflate(R.layout.activity_survey_listview_question, parent, false);

        // change couleur de fond
        layoutItem.setBackgroundColor(mContext.getResources().getColor(color.list_row_hover_start_color));

        //(2) : Récupération des TextView du layout
        TextView tvQuestionChoix = (TextView) layoutItem.findViewById(R.id.lvSurvey_Question_tvText);

        //(3) : Renseignement des valeurs
        tvQuestionChoix.setText(mQuestion.getChoix().get(position));

        ImageView imageView = (ImageView) layoutItem.findViewById(R.id.lvQuestion_img);
        // positionne réponse
        if (position == mQuestion.getCorrect()) {
            imageView.setImageResource(R.drawable.ic_ok);
            tvQuestionChoix.setTextColor(Color.GREEN);
        } else {
            if (position == mQuestion.getResponse()) {
                imageView.setImageResource(R.drawable.ic_no);
                tvQuestionChoix.setTextColor(Color.RED);
            }
        }

        //On mémorise la position dans le composant textview
        tvQuestionChoix.setTag(position);

        return layoutItem;
    }

}