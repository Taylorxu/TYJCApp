package com.wise.www.tyjcapp.main;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.databinding.FragmentFourthBinding;
import com.wise.www.tyjcapp.login.LoginActivity;
import com.wise.www.tyjcapp.main.ortherPage.ServerAddressActivity;

public class FourthFragment extends Fragment implements View.OnClickListener {
    FragmentFourthBinding binding;

    public FourthFragment() {

    }

    public static FourthFragment newInstance() {
        Bundle args = new Bundle();
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container, false);
        binding.title.setText(R.string.str_seeting);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//<21
            binding.btnLogOut.setBackgroundResource(R.drawable.shape_corners_2_primary);
        }else{
            binding.btnLogOut.setBackgroundResource(R.drawable.selector_ripple);
        }
        binding.tvVersion.setText(getLocalVersion(getContext()));
        binding.btnLogOut.setOnClickListener(this);
        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_out:
                LoginActivity.start(getContext());
                break;

        }
    }

    public static String getLocalVersion(Context ctx) {
        String localVersion = "0.0";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

}
