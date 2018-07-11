package com.wise.www.tyjcapp.login.view;

/**
 * Created by Administrator on 2018/3/20.
 */

public interface ILoginView {

    public void onLoginResult(Boolean result );

    public void onSetProgressBarVisibility(int visibility);

    void setViewEnable(boolean b);
}
