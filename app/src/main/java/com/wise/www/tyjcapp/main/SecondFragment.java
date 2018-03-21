package com.wise.www.tyjcapp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.BR;
import com.wise.www.tyjcapp.R;
import com.wise.www.basestone.view.adapter.XAdapter;
import com.wise.www.basestone.view.adapter.XViewHolder;
import com.wise.www.tyjcapp.bean.MemberTradeBean;
import com.wise.www.tyjcapp.databinding.FragmentSecondBinding;
import com.wise.www.tyjcapp.databinding.ItemMemberTradeTopBinding;

import java.util.ArrayList;


public class SecondFragment extends Fragment {
    FragmentSecondBinding binding;
    private ArrayList<MemberTradeBean> list;
    private int[] colorspan;

    public SecondFragment() {

    }

    public static SecondFragment newInstance() {
        SecondFragment secondFragment = new SecondFragment();
        return secondFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        colorspan = new int[]{getResources().getColor(R.color.color_top1_b), getResources().getColor(R.color.color_top2_b), getResources().getColor(R.color.color_top3_b)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);
        initView();
        return binding.getRoot();
    }


    XAdapter<MemberTradeBean, ItemMemberTradeTopBinding> adapter = new XAdapter.SimpleAdapter<MemberTradeBean, ItemMemberTradeTopBinding>(0, R.layout.item_member_trade_top) {

        @Override
        public void onBindViewHolder(XViewHolder<MemberTradeBean, ItemMemberTradeTopBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            if (position == 0 || position == 1 || position == 2) {
                holder.getBinding().textNo.setTextColor(Color.RED);
                holder.getBinding().textTradeNo.setTextColor(Color.RED);
                holder.getBinding().layoutItem.setBackgroundColor(colorspan[position]);
            }
            String No = String.valueOf(position + 1);
            if (position < 9) No = "0" + No;
            holder.getBinding().textNo.setText(No.toString());

            holder.getBinding().textMemberName.setText(getItemData(position).getMembername());
            holder.getBinding().textTradeNo.setText(getItemData(position).getTradeNumber());
        }
    };

    @SuppressLint("ResourceAsColor")
    private void initView() {
        crea();
        binding.title.setText(R.string.str_top);
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.contentAnnounceList.setLayoutManager(layoutManager);
        binding.contentAnnounceList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.contentAnnounceList.setAdapter(adapter);
        adapter.setList(list);

    }

    private void crea() {
        binding.refreshLayout.setRefreshing(true);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MemberTradeBean tradeBean = new MemberTradeBean();
            tradeBean.setMembername("网银系统啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊" + i);
            tradeBean.setTradeNumber("10000" + i);
            list.add(tradeBean);
        }
        binding.refreshLayout.setRefreshing(false);
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            crea();
        }
    };

}
