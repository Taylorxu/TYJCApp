package com.wise.www.tyjcapp.main.ortherPage;


import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.basestone.view.adapter.XAdapter;
import com.wise.www.basestone.view.view.PagingRecyclerView;
import com.wise.www.tyjcapp.BR;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.bean.WarningBean;
import com.wise.www.tyjcapp.databinding.FragmentWarningHistoryListBinding;
import com.wise.www.tyjcapp.databinding.ItemWarningListBinding;
import com.wise.www.tyjcapp.main.helperClass.RVItemDecoration;

import java.util.ArrayList;

public class WarningHistoryListFragment extends Fragment {


    private FragmentWarningHistoryListBinding binding;
    private ArrayList<WarningBean> list;

    public WarningHistoryListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_warning_history_list, container, false);
        initView();
        return binding.getRoot();
    }


    XAdapter<WarningBean, ItemWarningListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_warning_list);

    private void initView() {
        createData();
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
//        binding.container.setOnLoadMoreListener(onLoadMoreListener);
        binding.container.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.container.addItemDecoration(new RVItemDecoration(20, getResources().getColor(R.color.color_line)));
        binding.container.setAdapter(adapter);
        adapter.setList(list);
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            binding.container.setState(PagingRecyclerView.State.Refresh);
        }
    };
    PagingRecyclerView.OnLoadMoreListener onLoadMoreListener = new PagingRecyclerView.OnLoadMoreListener() {
        @Override
        public void onLoadMore(int page) {
            createData();
        }
    };

    private void createData() {
        list = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            WarningBean warningBean = new WarningBean();
            warningBean.setWarnContent("这是告警内容");
            warningBean.setWarnDate("2018-03-22 09:08:08");
            warningBean.setWarnSovledDate("恢复时间:2018-03-21 09:08:08");
            list.add(warningBean);
        }
        binding.container.setState(PagingRecyclerView.State.NoMore);
    }


}
