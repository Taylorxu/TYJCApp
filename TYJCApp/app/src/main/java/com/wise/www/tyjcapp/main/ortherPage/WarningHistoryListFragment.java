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
import com.wise.www.basestone.view.network.FlatMapResponse;
import com.wise.www.basestone.view.network.FlatMapTopRes;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.basestone.view.view.PagingRecyclerView;
import com.wise.www.tyjcapp.BR;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.bean.AlarmSystemBean;
import com.wise.www.tyjcapp.bean.WarningBean;
import com.wise.www.tyjcapp.databinding.FragmentWarningHistoryListBinding;
import com.wise.www.tyjcapp.databinding.ItemWarningListBinding;
import com.wise.www.tyjcapp.main.helperClass.RVItemDecoration;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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


    XAdapter<AlarmSystemBean, ItemWarningListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_warning_list);

    private void initView() {
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.container.setOnLoadMoreListener(onLoadMoreListener);
        binding.container.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.container.addItemDecoration(new RVItemDecoration(20, getResources().getColor(R.color.color_line)));
        binding.container.setAdapter(adapter);
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
            createData(page);
        }
    };

    private void createData(final int page) {
        ApiService.Creator.get().historyAlarmServlet(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<AlarmSystemBean>>>())
                .flatMap(new FlatMapTopRes<List<AlarmSystemBean>>())
                .subscribe(new Subscriber<List<AlarmSystemBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        binding.refreshLayout.setRefreshing(false);
                        binding.container.setState(PagingRecyclerView.State.LoadFail);
                    }

                    @Override
                    public void onNext(List<AlarmSystemBean> list) {
                        if (page == 1) {
                            adapter.setList(list);
                        } else {
                            adapter.addItems(list);
                        }
                        binding.refreshLayout.setRefreshing(false);
                        binding.container.setState(list.size() < 10 ? PagingRecyclerView.State.NoMore : PagingRecyclerView.State.LoadSuccess);
                    }
                });
    }


}
