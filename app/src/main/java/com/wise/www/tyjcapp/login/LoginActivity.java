package com.wise.www.tyjcapp.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wise.www.tyjcapp.MainActivity;
import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.databinding.ActivityLoginBinding;
import com.wise.www.tyjcapp.login.presenter.LoginPresenterCompl;
import com.wise.www.tyjcapp.login.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    ActivityLoginBinding loginBinding;
    LoginPresenterCompl presenterCompl;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        presenterCompl = new LoginPresenterCompl(this);
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        if (code == -1) {
            presenterCompl.setViewEnable(true);
            presenterCompl.setProgressBarVisiblity(View.GONE);
            loginBinding.signInButton.setEnabled(true);
            loginBinding.account.setEnabled(true);
            loginBinding.password.setEnabled(true);
        } else {
            MainActivity.start(this);
            finish();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        loginBinding.loginProgress.setVisibility(visibility);
    }

    @Override
    public void setViewEnable(boolean b) {
        loginBinding.signInButton.setEnabled(b);
        loginBinding.account.setEnabled(b);
        loginBinding.password.setEnabled(b);

    }


    public void onClick(View view) {
        presenterCompl.setViewEnable(false);
        presenterCompl.setProgressBarVisiblity(View.VISIBLE);
        presenterCompl.doLogin(loginBinding.account.getText().toString(), loginBinding.password.getText().toString());
    }


}
