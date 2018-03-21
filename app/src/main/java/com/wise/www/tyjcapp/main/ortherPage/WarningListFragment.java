package com.wise.www.tyjcapp.main.ortherPage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarningListFragment extends Fragment {


    public WarningListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_warning_list, container, false);
    }

}
