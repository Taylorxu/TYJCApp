package com.wise.www.tyjcapp.main.request;


import com.wise.www.tyjcapp.main.helperClass.sharedPreference.MySharedpreferences;

public class BaseUrl {
    public static final boolean DEBUG = false;


    public static String getHost() {
        String Host = MySharedpreferences.getServerString() + "services/";
        if (Host.indexOf("http://") > -1||Host.indexOf("HTTP://") > -1) {
            return Host;
        } else {
            return "http://" + Host;
        }
    }
}
