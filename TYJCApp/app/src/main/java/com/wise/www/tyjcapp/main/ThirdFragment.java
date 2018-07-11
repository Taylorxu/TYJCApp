package com.wise.www.tyjcapp.main;

import android.content.Context;
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
import com.wise.www.tyjcapp.main.ortherPage.WarningPageAdapter;


public class ThirdFragment extends Fragment implements View.OnClickListener {
    FragmentThirdBinding binding;
    private String[] pageTitle;

    public static ThirdFragment newInstance() {
        ThirdFragment secondFragment = new ThirdFragment();
        return secondFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pageTitle = new String[]{getResources().getString(R.string.str_warning_list), getResources().getString(R.string.str_warning_history)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.title.setText(R.string.str_warning_title);
        binding.contentVp.setAdapter(new WarningPageAdapter(getChildFragmentManager(), pageTitle));
        binding.warningTabL.setupWithViewPager(binding.contentVp);
    }

    public void onClick(View view) {

    }

}
