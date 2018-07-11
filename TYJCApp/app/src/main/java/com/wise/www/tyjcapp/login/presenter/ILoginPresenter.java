package com.wise.www.tyjcapp.login.presenter;

/**
 * Created by Administrator on 2018/3/20.
 */

public interface ILoginPresenter {
    void doLogin(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);
    void setViewEnable(boolean b);
}
