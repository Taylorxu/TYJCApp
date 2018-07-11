package com.wise.www.basestone.view.helper;

import android.widget.Toast;

import com.wise.www.basestone.view.WApp;

/**
 * Created by Administrator on 2018/3/16.
 */

public class EEMsgToastHelper {
    public EEMsgToastHelper() {

    }

    public static EEMsgToastHelper newInstance() {
        EEMsgToastHelper fragment = new EEMsgToastHelper();
        return fragment;
    }

    public void selectWitch(String exception) {
        if(exception.indexOf("Network is unreachable")>-1){
            toastMesg("请检查网络连接情况");
            return;
        }
        if (exception.indexOf("Failed to connect") > -1) {
            toastMesg("服务连接失败");
            return;
        } else if (exception.indexOf("timeout") > -1) {
            toastMesg("连接超时");
            return;
        }else if (exception.indexOf("Unable to resolve host")>-1){
            toastMesg("服务地址错误");
            return;
        } else {
            toastMesg(exception);
        }
    }

    public void toastMesg(String msg) {
        Toast.makeText(WApp.getInstance().getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
