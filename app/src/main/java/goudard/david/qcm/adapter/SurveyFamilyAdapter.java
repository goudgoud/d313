package goudard.david.qcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import goudard.david.qcm.R;
import goudard.david.qcm.entity.SurveyFamily;

/**
 * SurveyFamily Adapter
 *
 * @author David GOUDARD
 * @version 1
 * @since 23 /12/2016
 */
public class SurveyFamilyAdapter extends BaseAdapter {

    /**
     * List of family surveys
     */
    private List<SurveyFamily> mListSurveyFamilly;

    /**
     * Manage graphical show
     */
    private LayoutInflater mInflater;

    /**
     * List of listeners
     */
    private ArrayList<SurveyFamilyAdapterListenerInterface> mListListener = new ArrayList<SurveyFamilyAdapterListenerInterface>();

    /**
     * Constructor
     *
     * @param context           Context of adapter
     * @param aListSurveyFamily List of family surveys
     */
    public SurveyFamilyAdapter(Context context, List<SurveyFamily> aListSurveyFamily) {
        //The adapter Context
        this.mListSurveyFamilly = aListSurveyFamily;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mListSurveyFamilly.size();
    }

    public Object getItem(int position) {
        return mListSurveyFamilly.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.activity_main_listview_qcm, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        //(2) : Récupération des TextView du layout
        TextView tvSurveyFamily = (TextView) layoutItem.findViewById(R.id.lvMain_SurveyFamily_tvText);

        //(3) : Renseignement des valeurs
        tvSurveyFamily.setText(mListSurveyFamilly.get(position).getName());

        //On mémorise la position dans le composant textview
        layoutItem.setTag(position);
        //On ajoute un listener
        layoutItem.setOnClickListener(new View.OnClickListener() {
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

    /**
     * Listener
     *
     * @param aListener SurveyFamilyAdapterListenerInterface
     */
    public void addListener(SurveyFamilyAdapterListenerInterface aListener) {
        mListListener.add(aListener);
    }

    /**
     * Send listeners
     *
     * @param item     SurveyFamily
     * @param position int
     */
    private void sendListener(SurveyFamily item, int position) {
        for (int i = mListListener.size() - 1; i >= 0; i--) {
            mListListener.get(i).onClickSurveyFamily(item, position);
        }
    }
}
