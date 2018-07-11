package com.wise.www.tyjcapp.main.ortherPage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.databinding.ActivityServerAddressBinding;
import com.wise.www.tyjcapp.login.LoginActivity;
import com.wise.www.tyjcapp.main.helperClass.sharedPreference.MySharedpreferences;
import com.wise.www.tyjcapp.main.helperClass.sharedPreference.Protocol;
import com.wise.www.tyjcapp.main.request.ApiService;

public class ServerAddressActivity extends AppCompatActivity {
    ActivityServerAddressBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, ServerAddressActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_server_address);
        binding.title.setText(getResources().getString(R.string.str_service_set));

        String hostUrl = Protocol.getHostUrl();
        if (!TextUtils.isEmpty(hostUrl)) {
            binding.etServerUrl.setText(hostUrl);
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.bt_server_save:
                saveAddress();
                break;
        }

    }

    private void saveAddress() {
        String server_url = binding.etServerUrl.getText().toString().trim();
        if (TextUtils.isEmpty(server_url)) {
            Toast.makeText(this, getResources().getString(R.string.toast_server), Toast.LENGTH_SHORT).show();
            return;
        }else if(!server_url.endsWith("/")){
            Toast.makeText(this, getResources().getString(R.string.toast_server_end), Toast.LENGTH_SHORT).show();
            return;
        }
        MySharedpreferences.putServerString(server_url, new Callback() {
            @Override
            public void onStartLogin() {
                Toast.makeText(ServerAddressActivity.this, getResources().getString(R.string.toast_server_save), Toast.LENGTH_SHORT).show();
                ApiService.Creator.setNull();
                LoginActivity.start(ServerAddressActivity.this);
                finish();
            }

            @Override
            public void onFail() {
                Toast.makeText(ServerAddressActivity.this, getResources().getString(R.string.toast_server_save), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface Callback {
        void onStartLogin();

        void onFail();
    }
}
