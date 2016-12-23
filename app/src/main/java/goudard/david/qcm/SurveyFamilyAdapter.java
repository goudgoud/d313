package goudard.david.qcm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        //On retourne l'item créé.
        return layoutItem;
    }
}
