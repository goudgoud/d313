package goudard.david.qcm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 23/12/16.
 */

public class SurveyFamilyAdapter extends BaseAdapter {

    // Une liste de familles de questionnaires
    private List<SurveyFamily> mListSurveyFamilly;

    //Le contexte dans lequel est présent l' adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    //Contient la liste des listeners
    private ArrayList<SurveyFamilyAdapterListener> mListListener = new ArrayList<SurveyFamilyAdapterListener>();


    public SurveyFamilyAdapter(Context context, List<SurveyFamily> aListSurveyFamily) {
        this.mContext = context;
        this.mListSurveyFamilly = aListSurveyFamily;
        this.mInflater = LayoutInflater.from(mContext);
    }

    // Comptage des items
    public int getCount() {
        return mListSurveyFamilly.size();
    }

    // Identification des items
    // par position
    public Object getItem(int position) {
        return mListSurveyFamilly.get(position);
    }

    // par id
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.activity_main_listview_qcm, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView du layout
        TextView tvSurveyFamily = (TextView) layoutItem.findViewById(R.id.lvQuestion_tvText);

        //(3) : Renseignement des valeurs
        tvSurveyFamily.setText(mListSurveyFamilly.get(position).getName());

        //On mémorise la position dans le composant textview
        tvSurveyFamily.setTag(position);
        //On ajoute un listener
        tvSurveyFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "SurveyFamily"
                Integer position = (Integer) v.getTag();
                //On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
                sendListener(mListSurveyFamilly.get(position), position);
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
    public void addListener(SurveyFamilyAdapterListener aListener) {
        mListListener.add(aListener);
    }

    private void sendListener(SurveyFamily item, int position) {
        for (int i = mListListener.size() - 1; i >= 0; i--) {
            mListListener.get(i).onClickNom(item, position);
        }
    }
}
