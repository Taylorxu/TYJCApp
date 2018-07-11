package com.wise.www.tyjcapp.main.ortherPage;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.wise.www.tyjcapp.bean.SystemCaseBean;
import com.wise.www.tyjcapp.bean.SystemWorkingCaseBean;
import com.wise.www.tyjcapp.bean.WarningBean;
import com.wise.www.tyjcapp.databinding.FragmentWarningListBinding;
import com.wise.www.tyjcapp.databinding.ItemWarningListBinding;
import com.wise.www.tyjcapp.main.helperClass.RVItemDecoration;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class WarningListFragment extends Fragment {
    private ArrayList<WarningBean> list;

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

    XAdapter<AlarmSystemBean, ItemWarningListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_warning_list);

    private void initView() {
        createData("1");
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.container.setOnLoadMoreListener(onLoadMoreListener);
        binding.container.addItemDecoration(new RVItemDecoration(20, getResources().getColor(R.color.color_line)));
        binding.container.setLayoutManager(new LinearLayoutManager(getContext()));
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
            createData("1");
        }
    };

    private void createData(String page) {
        ApiService.Creator.get().currentAlarmServlet(page)
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
                        adapter.setList(list);
                        binding.refreshLayout.setRefreshing(false);
                        binding.container.setState(PagingRecyclerView.State.LoadSuccess);
                    }
                });

    }

}
