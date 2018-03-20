package com.wise.www.tyjcapp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.databinding.FragmentFirstBinding;


public class FirstFragment extends Fragment {
    FragmentFirstBinding fragmentBinding;

    public static FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        fragmentBinding.title.setText(R.string.str_system_case);
        return fragmentBinding.getRoot();
    }


}
