package com.wise.www.tyjcapp.main.ortherPage;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.basestone.view.adapter.XAdapter;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.bean.WarningBean;
import com.wise.www.tyjcapp.databinding.FragmentWarningListBinding;

/**
 *
 */
public class WarningListFragment extends Fragment {
    public WarningListFragment() {

    }

    FragmentWarningListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_warning_list, container, false);
        initView();
        return binding.getRoot();
    }
    private void initView() {
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    }

}
