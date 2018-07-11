package com.wise.www.tyjcapp.main.ortherPage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.wise.www.basestone.view.adapter.XAdapter;
import com.wise.www.basestone.view.adapter.XViewHolder;
import com.wise.www.basestone.view.network.FlatMapResponse;
import com.wise.www.basestone.view.network.FlatMapTopRes;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.basestone.view.util.LogUtils;
import com.wise.www.tyjcapp.BR;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.bean.ItemSingleLineText;
import com.wise.www.tyjcapp.bean.SystemWorkingCaseBean;
import com.wise.www.tyjcapp.databinding.ActivitySearchPageBinding;
import com.wise.www.tyjcapp.databinding.ItemSearchBankBinding;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPageActivity extends AppCompatActivity implements TextWatcher {


    public static void start(Context context) {
        Intent starter = new Intent(context, SearchPageActivity.class);
        context.startActivity(starter);
    }

    List<SystemWorkingCaseBean> selectEdList = new ArrayList<>();
    ActivitySearchPageBinding binding;
    XAdapter<SystemWorkingCaseBean, ItemSearchBankBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_search_bank);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSysBank();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_page);
        binding.title.setText(getResources().getString(R.string.str_bank_select));
        binding.textBankSearch.addTextChangedListener(this);
        binding.contentBankList.setLayoutManager(new LinearLayoutManager(this));
        binding.contentBankList.setAdapter(adapter);
        adapter.setItemClickListener(itemClickListener);
    }

    XAdapter.OnItemClickListener<SystemWorkingCaseBean, ItemSearchBankBinding> itemClickListener = new XAdapter.OnItemClickListener<SystemWorkingCaseBean, ItemSearchBankBinding>() {
        @Override
        public void onItemClick(XViewHolder<SystemWorkingCaseBean, ItemSearchBankBinding> holder) {
            Intent intent = new Intent();
            intent.putExtra("TradeBankName", holder.getBinding().getData().getTradeBankName());
            intent.putExtra("TradeBankCode", holder.getBinding().getData().getTradeBankCode());
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    public void getSysBank() {
        ApiService.Creator.get().dictDataServlet("Bank")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<SystemWorkingCaseBean>>>())
                .flatMap(new FlatMapTopRes<List<SystemWorkingCaseBean>>())
                .subscribe(new Subscriber<List<SystemWorkingCaseBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<SystemWorkingCaseBean> list) {
                        SystemWorkingCaseBean bean = new SystemWorkingCaseBean();
                        bean.setTradeBankName("全部");
                        bean.setTradeBankCode("-1");
                        list.add(0, bean);
                        adapter.setList(list);
                        selectEdList.addAll(list);
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        adapter.removeAll();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        for (SystemWorkingCaseBean caseBean : selectEdList) {
            if (caseBean.getTradeBankName().indexOf(s.toString()) > -1) {
                adapter.addItem(caseBean);
            }
        }
    }

    public void onClick(View view) {
        finish();
    }


}
