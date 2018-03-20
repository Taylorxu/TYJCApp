package com.wise.www.tyjcapp.login.presenter;

import android.os.Handler;
import android.os.Looper;

import com.wise.www.tyjcapp.login.LoginActivity;
import com.wise.www.tyjcapp.login.model.IUser;
import com.wise.www.tyjcapp.login.model.UserModel;
import com.wise.www.tyjcapp.login.view.ILoginView;

/**
 * Created by Administrator on 2018/3/20.
 */

public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    Handler handler;
    IUser iUser;
    public LoginPresenterCompl(LoginActivity loginActivity) {
        iLoginView = loginActivity;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void doLogin(String name, String passwd) {
        Boolean isLoginSuccess = true;
        final int code = iUser.checkUserValidity(name,passwd);
        if (code!=0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 5000);
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }

    @Override
    public void setViewEnable(boolean b) {
        iLoginView.setViewEnable(b);
    }


    private void initUser() {
         iUser = new UserModel("100", "100");
    }
}
