package com.wise.www.tyjcapp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.BR;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.adapter.XAdapter;
import com.wise.www.tyjcapp.adapter.XViewHolder;
import com.wise.www.tyjcapp.bean.SystemCaseBean;
import com.wise.www.tyjcapp.databinding.FragmentFirstBinding;
import com.wise.www.tyjcapp.databinding.ItemFirstFragmentBinding;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {
    FragmentFirstBinding fragmentBinding;
    private List<SystemCaseBean> list = new ArrayList<>();

    public static FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        fragmentBinding.title.setText(R.string.str_system_case);
        initListView();
        return fragmentBinding.getRoot();
    }

    XAdapter<SystemCaseBean, ItemFirstFragmentBinding> xAdapter = new XAdapter.SimpleAdapter<SystemCaseBean, ItemFirstFragmentBinding>(0, R.layout.item_first_fragment) {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(XViewHolder<SystemCaseBean, ItemFirstFragmentBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.getBinding().textName.setText(getItemData(position).getName());
            holder.getBinding().textValue.setText(getItemData(position).getValue());
            holder.getBinding().waveLoadingView.setCenterTitle(getItemData(position).getPercent() + "%");
            holder.getBinding().waveLoadingView.setProgressValue(getItemData(position).getPercent());
            holder.getBinding().waveLoadingView.setCenterTitleColor(Color.WHITE);
            holder.getBinding().waveLoadingView.setBorderColor(R.color.color_4d);
            holder.getBinding().waveLoadingView.setBorderWidth(1f);
            holder.getBinding().waveLoadingView.setWaveColor(Color.YELLOW);
            holder.getBinding().waveLoadingView.setWaveBgColor(Color.CYAN);


        }
    };

    /**
     *
     */
    private void initListView() {

        createData();
        StaggeredGridLayoutManager layoutmanager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        fragmentBinding.contentCase.setLayoutManager(layoutmanager);
        fragmentBinding.contentCase.setAdapter(xAdapter);
        xAdapter.setList(list);
    }

    private void createData() {
        for (int i = 0; i < 20; i++) {
            SystemCaseBean systemCaseBean = new SystemCaseBean();
            systemCaseBean.setName("网银系统" + i);
            systemCaseBean.setValue(2000 + i + "");
            systemCaseBean.setPercent(i + "");
            list.add(systemCaseBean);
        }
    }


}
