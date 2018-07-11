package com.wise.www.tyjcapp.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wise.www.tyjcapp.R;
import com.wise.www.tyjcapp.databinding.FragmentFourthBinding;
import com.wise.www.tyjcapp.databinding.LogOutDialogViewBinding;
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
        } else {
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
                showDialog();
                break;

        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.create();
        LogOutDialogViewBinding dialogViewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.log_out_dialog_view, null, false);
        dialogViewBinding.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogViewBinding.btEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.start(getContext());
                getActivity().finish();
            }
        });

        dialog.setView(dialogViewBinding.getRoot());
        dialog.show();
        Display d = getActivity().getWindowManager().getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.25);
        p.width = (int) (d.getWidth() * 0.7);
        dialog.getWindow().setAttributes(p);

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
