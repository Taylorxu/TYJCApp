package com.wise.www.tyjcapp.main;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wise.www.basestone.view.WApp;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Administrator on 2018/1/30.
 */

public class MyApplication extends WApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        configRealm();
        frescoConfig();
    }

    private void frescoConfig() {
        Fresco.initialize(getInstance());//初始化Fresc
    }

    private void configRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("Tongyi.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }



}
