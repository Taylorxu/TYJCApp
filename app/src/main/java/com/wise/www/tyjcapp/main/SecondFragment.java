package com.wise.www.tyjcapp.main;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.databinding.FragmentSecondBinding;



public class SecondFragment extends Fragment {
    FragmentSecondBinding binding;

    public SecondFragment() {

    }

    public static SecondFragment newInstance() {
        SecondFragment secondFragment = new SecondFragment();
        return secondFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);
        initView();
        return binding.getRoot();
    }


    @SuppressLint("ResourceAsColor")
    private void initView() {
        binding.title.setText(R.string.str_top);
      /*  binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.contentAnnounceList.setLayoutManager(layoutManager);
        binding.contentAnnounceList.setAdapter(adapter);*/

    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {


        }
    };

}
