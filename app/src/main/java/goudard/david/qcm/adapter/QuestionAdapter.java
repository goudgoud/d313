package goudard.david.qcm.adapter;

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

import goudard.david.qcm.R;
import goudard.david.qcm.entity.Question;
import goudard.david.qcm.entity.Survey;

/**
 * Created by david on 24/12/16.
 */

/**
 * Question Adapter
 *
 * @author david
 * @version 1
 * @since 24/12/2016
 */
public class QuestionAdapter extends BaseAdapter {

    /**
     * List of possible choices
     */
    protected List<String> mListChoix;

    /**
     * Context of adapter
     */
    protected Context mContext;

    /**
     * The layout inflater
     */
    protected LayoutInflater mInflater;

    /**
     * The current question
     */
    protected Question mQuestion;

    /**
     * List of Listeners
     */
    private ArrayList<QuestionAdapterListenerInterface> mListListener = new ArrayList<QuestionAdapterListenerInterface>();


    /**
     * Constructor
     *
     * @param context  Context parent
     * @param question Question in progress
     */
    public QuestionAdapter(Context context, Question question) {
        this.mContext = context;
        this.mQuestion = question;
        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * Comptage des items
     *
     * @return items amount
     */
    public int getCount() {
        return mQuestion.getChoix().size();
    }

    /**
     * Choices identification by position
     *
     * @param position in ListView
     * @return Object at position in list
     */
    public Object getItem(int position) {
        return mQuestion.getChoix().get(position);
    }

    /**
     * Identification by id
     *
     * @param position int
     * @return long
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Show line of listview
     *
     * @param position    int
     * @param convertView View
     * @param parent      ViewGroup
     * @return View
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du layout XML
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.activity_survey_listview_question, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView du layout
        TextView tvQuestion = (TextView) layoutItem.findViewById(R.id.lvSurvey_Question_tvText);

        //(3) : Renseignement des valeurs
        tvQuestion.setText(mQuestion.getChoix().get(position));

        //On mémorise la position dans le composant textview
        layoutItem.setTag(position);
        //On ajoute un listener
        layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Survey"
                Integer position = (Integer) v.getTag();
                //On prévient les listeners qu'il y a eu un clic sur le TextView
                sendListener(mQuestion.getChoix().get(position), position);
            }
        });

        //On retourne l'item créé.
        return layoutItem;
    }

    /**
     * Listener
     *
     * @param aListener QuestionAdapterListenerInterface
     */
    public void addListener(QuestionAdapterListenerInterface aListener) {
        mListListener.add(aListener);
    }

    /**
     * Send listener
     *
     * @param item     String of choice of response
     * @param position int
     */
    private void sendListener(String item, int position) {
        for (int i = mListListener.size() - 1; i >= 0; i--) {
            mListListener.get(i).onClickQuestion(item, position);
        }
    }
}