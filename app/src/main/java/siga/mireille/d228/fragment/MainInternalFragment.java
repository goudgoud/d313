package siga.mireille.d228.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import siga.mireille.d228.R;

/**
 * MainInternalFragment class
 *
 * @author SIGA Mireille
 * @version 1
 * @since 30 /12/2016
 */
public class MainInternalFragment extends Fragment {

    /**
     * Instantiates a new Main internal fragment.
     */
    public MainInternalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SquareFragment.
     */
    public static siga.mireille.d228.fragment.MainInternalFragment newInstance(int param1) {
        siga.mireille.d228.fragment.MainInternalFragment fragment = new siga.mireille.d228.fragment.MainInternalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Inflate the layout for this fragment
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return LayoutInflater
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_internal_fragment, container, false);
    }
}
