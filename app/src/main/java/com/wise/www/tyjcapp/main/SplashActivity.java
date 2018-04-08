package com.wise.www.tyjcapp.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.wise.www.basestone.view.helper.EEMsgToastHelper;
import com.wise.www.basestone.view.network.FlatMapResponse;
import com.wise.www.basestone.view.network.FlatMapTopRes;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.bean.SystemCaseBean;
import com.wise.www.tyjcapp.bean.SystemWorkingCaseBean;
import com.wise.www.tyjcapp.login.LoginActivity;
import com.wise.www.tyjcapp.login.model.User;
import com.wise.www.tyjcapp.main.helperClass.sharedPreference.MySharedpreferences;
import com.wise.www.tyjcapp.main.helperClass.sharedPreference.Protocol;
import com.wise.www.tyjcapp.main.ortherPage.ServerAddressActivity;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {
    Bundle bundle;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    bundle = null;
                    MainActivity.start(getBaseContext(), bundle);
                    finish();
                    break;
                case 0:
                    MainActivity.start(getBaseContext(), bundle);
                    finish();
                    break;
                case 1:
                    Intent intent = new Intent(SplashActivity.this, ServerAddressActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    LoginActivity.start(SplashActivity.this);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkEveryThing();
    }

    /**
     * 该成员行 系统数据 集 用在firstfragment 中，结果 {
     * "TradeSysName": "大额汇票",
     * "TradeSysCode": "LARGE_QUOTA_BILL",
     * "TradeSysColour": "#87cefa"
     * }
     * 这样 就可以在 firstfragment中只查询
     */
    private void getBaseData() {
        ApiService.Creator.get().dictDataServlet("BusinessSystem")
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
                        handler.sendEmptyMessage(-1);
                    }

                    @Override
                    public void onNext(List<SystemWorkingCaseBean> list) {
                        bundle = new Bundle();
                        bundle.putParcelableArrayList("systemData", (ArrayList<? extends Parcelable>) list);
                        handler.sendEmptyMessage(0);
                    }
                });
    }

    private void checkEveryThing() {
        //服务器地址
        String hostUrl = Protocol.getHostUrl();
        if (MySharedpreferences.getFirstStatusBoolean()) {
            if (null == User.getCurrentUser()) {
                if (hostUrl.isEmpty()) {
                    handler.sendEmptyMessage(1);
                    return;
                } else {
                    handler.sendEmptyMessage(2);
                }
            }

        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getBaseData();
                }
            }).start();
        }
    }
}
