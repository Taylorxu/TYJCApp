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

import com.wise.www.basestone.view.helper.EEMsgToastHelper;
import com.wise.www.basestone.view.network.FlatMapResponse;
import com.wise.www.basestone.view.network.FlatMapTopRes;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.basestone.view.view.PagingRecyclerView;
import com.wise.www.tyjcapp.BR;
import com.wise.www.tyjcapp.R;
import com.wise.www.basestone.view.adapter.XAdapter;
import com.wise.www.basestone.view.adapter.XViewHolder;
import com.wise.www.tyjcapp.bean.AlarmSystemBean;
import com.wise.www.tyjcapp.bean.MemberTradeBean;
import com.wise.www.tyjcapp.bean.WrapMemberTradeBean;
import com.wise.www.tyjcapp.databinding.FragmentSecondBinding;
import com.wise.www.tyjcapp.databinding.ItemMemberTradeTopBinding;
import com.wise.www.tyjcapp.main.helperClass.sharedPreference.Constant;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SecondFragment extends Fragment {
    FragmentSecondBinding binding;
    SecondFragmentIFlistenter fragmentIFlistenter;
    private int[] colorspan;


    public static SecondFragment newInstance() {
        SecondFragment secondFragment = new SecondFragment();
        return secondFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SecondFragmentIFlistenter) {
            fragmentIFlistenter = (SecondFragmentIFlistenter) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        colorspan = new int[]{getResources().getColor(R.color.color_top1_b), getResources().getColor(R.color.color_top2_b), getResources().getColor(R.color.color_top3_b)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);
        initView();
        return binding.getRoot();
    }


    XAdapter<MemberTradeBean, ItemMemberTradeTopBinding> adapter = new XAdapter.SimpleAdapter<MemberTradeBean, ItemMemberTradeTopBinding>(0, R.layout.item_member_trade_top) {

        @Override
        public void onBindViewHolder(XViewHolder<MemberTradeBean, ItemMemberTradeTopBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.getBinding().setData(getItemData(position));
            if (position == 0 || position == 1 || position == 2) {
                holder.getBinding().textNo.setTextColor(Color.RED);
                holder.getBinding().textTradeNo.setTextColor(Color.RED);
                holder.getBinding().layoutItem.setBackgroundColor(colorspan[position]);
            }
            String No = String.valueOf(position + 1);
            if (position < 9) No = "0" + No;
            holder.getBinding().textNo.setText(No.toString());

            holder.getBinding().textMemberName.setText(getItemData(position).getTradeBankName());
            holder.getBinding().textTradeNo.setText(getItemData(position).getTradeVolume());
        }
    };
    XAdapter.OnItemClickListener<MemberTradeBean, ItemMemberTradeTopBinding> itemClickListener = new XAdapter.OnItemClickListener<MemberTradeBean, ItemMemberTradeTopBinding>() {
        @Override
        public void onItemClick(XViewHolder<MemberTradeBean, ItemMemberTradeTopBinding> holder) {
            fragmentIFlistenter.refreshByBankCode(holder.getBinding().getData());
        }
    };

    @SuppressLint("ResourceAsColor")

    private void initView() {
        binding.refreshLayout.setRefreshing(true);
        createData();

        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.contentAnnounceList.setLayoutManager(layoutManager);
        binding.contentAnnounceList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.contentAnnounceList.setAdapter(adapter);
        adapter.setItemClickListener(itemClickListener);

    }

    private void createData() {

        ApiService.Creator.get().tradeBankTopServlet()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<WrapMemberTradeBean>>())
                .flatMap(new FlatMapTopRes<WrapMemberTradeBean>())
                .subscribe(new Subscriber<WrapMemberTradeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        binding.refreshLayout.setRefreshing(false);
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        setEmptyView(true);
                    }

                    @Override
                    public void onNext(WrapMemberTradeBean bean) {
                        setEmptyView(bean == null ? true : false);
                        adapter.setList(bean.getBankList());
                        binding.title.setText("成员行" + bean.getHour() + "小时内交易量Top");
                        binding.refreshLayout.setRefreshing(false);

                    }
                });
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            createData();
        }
    };

    public interface SecondFragmentIFlistenter {

        void refreshByBankCode(MemberTradeBean data);
    }

    private void setEmptyView(boolean isEmpty) {
        if (isEmpty) {
            binding.contentAnnounceList.setVisibility(View.GONE);
            binding.tvEmptyView.setVisibility(View.VISIBLE);
            binding.tvEmptyView.setText(getResources().getString(R.string.str_empty_data1));
        } else {
            binding.contentAnnounceList.setVisibility(View.VISIBLE);
            binding.tvEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fragmentIFlistenter != null) fragmentIFlistenter = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentIFlistenter = null;
    }
}
