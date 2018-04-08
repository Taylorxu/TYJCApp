package com.wise.www.tyjcapp.login.presenter;

import android.os.Handler;
import android.os.Looper;

import com.wise.www.basestone.view.helper.EEMsgToastHelper;
import com.wise.www.basestone.view.network.FlatMapResponse;
import com.wise.www.basestone.view.network.FlatMapTopRes;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.tyjcapp.login.LoginActivity;
import com.wise.www.tyjcapp.login.view.ILoginView;
import com.wise.www.tyjcapp.main.request.ApiService;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/20.
 */

public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    Handler handler;

    public LoginPresenterCompl(LoginActivity loginActivity) {
        iLoginView = loginActivity;
        handler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void doLogin(String name, String passwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountNum", name);
        map.put("password", passwd);
        ApiService.Creator.get().userLoginServlet("UserLoginServlet", map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<Void>>())
                .flatMap(new FlatMapTopRes<Void>())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        iLoginView.onLoginResult(true);
                    }
                });
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }

    @Override
    public void setViewEnable(boolean b) {
        iLoginView.setViewEnable(b);
    }


}
