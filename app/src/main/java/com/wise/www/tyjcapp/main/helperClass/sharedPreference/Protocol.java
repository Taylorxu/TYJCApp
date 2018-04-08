package com.wise.www.tyjcapp.main.helperClass.sharedPreference;

/**
 * Created by Administrator on 2018/4/8.
 */

public class Protocol {

    /*获取url*/
    public static String getHostUrl() {
        return MySharedpreferences.getServerString();
    }

}
