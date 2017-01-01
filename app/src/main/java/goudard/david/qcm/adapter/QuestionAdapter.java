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

public class QuestionAdapter extends BaseAdapter {

    // Une liste de questions
    protected List<String> mListChoix;

    //Le contexte dans lequel est présent l' adapter
    protected Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    protected LayoutInflater mInflater;

    protected Question mQuestion;

    //Contient la liste des listeners
    private ArrayList<QuestionAdapterListenerInterface> mListListener = new ArrayList<QuestionAdapterListenerInterface>();


    public QuestionAdapter(Context context, Question question) {
        this.mContext = context;
       // this.mListChoix = aListChoix;
        this.mQuestion = question;
        this.mInflater = LayoutInflater.from(mContext);
    }

    // Comptage des items
    public int getCount() {
        return mQuestion.getChoix().size();
    }

    // Identification des items
    // par position
    public Object getItem(int position) {
        return mQuestion.getChoix().get(position);
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
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.activity_survey_listview_question, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView du layout
        TextView tvQuestion = (TextView) layoutItem.findViewById(R.id.lvSurvey_Question_tvText);

        //(3) : Renseignement des valeurs
        tvQuestion.setText(mQuestion.getChoix().get(position));

        //On mémorise la position dans le composant textview
        tvQuestion.setTag(position);
        //On ajoute un listener
        tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Survey"
                Integer position = (Integer) v.getTag();
                //On prévient les listeners qu'il y a eu un clic sur le TextView
                sendListener(mQuestion.getChoix().get(position), position);
            }
        });

        ImageView imageView = (ImageView) layoutItem.findViewById(R.id.lvQuestion_img);

        //On retourne l'item créé.
        return layoutItem;
    }


    /***************************
     * Listener
     */

    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(QuestionAdapterListenerInterface aListener) {
        mListListener.add(aListener);
    }

    private void sendListener(String item, int position) {
        for (int i = mListListener.size() - 1; i >= 0; i--) {
            mListListener.get(i).onClickQuestion(item, position);
        }
    }
}