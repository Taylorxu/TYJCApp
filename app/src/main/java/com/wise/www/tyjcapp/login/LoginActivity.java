package com.wise.www.tyjcapp.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.login.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onLoginResult(Boolean result, int code) {

    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {

    }
}
