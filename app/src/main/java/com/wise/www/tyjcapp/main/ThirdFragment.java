package com.wise.www.tyjcapp.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.databinding.FragmentThirdBinding;



public class ThirdFragment extends Fragment implements View.OnClickListener {
    FragmentThirdBinding binding;
    public static ThirdFragment newInstance() {
        ThirdFragment secondFragment = new ThirdFragment();
        return secondFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.title.setText(R.string.str_warning_title);


    }

    public void onClick(View view) {

    }

}
