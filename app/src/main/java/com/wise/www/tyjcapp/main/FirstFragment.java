package com.wise.www.tyjcapp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
            holder.getBinding().waveLoadingView.setCenterTitleColor(Color.parseColor("#4d4d4d"));
            holder.getBinding().waveLoadingView.setBorderColor(R.color.color_line);
            holder.getBinding().waveLoadingView.setBorderWidth(1f);
            holder.getBinding().waveLoadingView.setWaveColor(Color.rgb(255, 198, 0));
            holder.getBinding().waveLoadingView.setWaveBgColor(Color.parseColor("#62FFC600"));


        }
    };

    /**
     *
     */
    private void initListView() {

        createData();
        GridLayoutManager layoutmanager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        fragmentBinding.contentCase.setLayoutManager(layoutmanager);
        fragmentBinding.contentCase.setAdapter(xAdapter);
        fragmentBinding.contentCase.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int space = 15;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = space;
                if (parent.getChildLayoutPosition(view) % 2 == 0) {
                    outRect.right = 2;
                }
            }
        });
        xAdapter.setList(list);
    }

    private void createData() {
        for (int i = 0; i < 21; i++) {
            SystemCaseBean systemCaseBean = new SystemCaseBean();
            systemCaseBean.setName("网银系统" + i);
            systemCaseBean.setValue(2000 + i + "");
            systemCaseBean.setPercent(20 + i + "");
            list.add(systemCaseBean);
        }
    }


}
