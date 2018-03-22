package com.wise.www.tyjcapp.login.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2018/3/22.
 */

public class User extends RealmObject{
    private static User current;
    @PrimaryKey
    int id;
    boolean isLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }


    public static User getCurrentUser() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> result = realm.where(User.class)
                        .equalTo("isLogin", true).findAll();
                if (result.size() == 1) {
                    current = result.first();
                } else if (result.size() > 1) {
                    for (User user : result) {
                        user.setLogin(false);
                    }
                }
            }
        });
        return current;
    }

    private static void setCurrent(final User current) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> result = realm.where(User.class).equalTo("isLogin", true).findAll();
                for (User user : result) {
                    user.setLogin(false);
                }
                if (current == null) {
                    User.current = null;
                    return;
                }
                current.setLogin(true);
                realm.copyToRealmOrUpdate(current);
            }
        });
    }

    public static void logout() {
        setCurrent(null);
    }

    public static void login(User user) {
        setCurrent(user);
    }


}
