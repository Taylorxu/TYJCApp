package com.wise.www.tyjcapp.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.databinding.FragmentFourthBinding;
import com.wise.www.tyjcapp.login.LoginActivity;
import com.wise.www.tyjcapp.main.ortherPage.ServerAddressActivity;

public class FourthFragment extends Fragment implements View.OnClickListener {
    FragmentFourthBinding binding;

    public FourthFragment() {

    }

    public static FourthFragment newInstance() {
        Bundle args = new Bundle();
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container, false);
        binding.title.setText(R.string.str_seeting);
        binding.flToService.setOnClickListener(this);
        binding.btnLogOut.setOnClickListener(this);
        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_out:
                LoginActivity.start(getContext());
                break;
            case R.id.fl_to_service:
                ServerAddressActivity.start(getContext());
                break;
        }
    }
}
