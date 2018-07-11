package com.wise.www.tyjcapp.bean;

/**
 * Created by Administrator on 2018/3/21.
 */

public class WarningBean {

    String warnDate;
    String warnContent;
    String warnSovledDate;

    public String getWarnDate() {
        return warnDate;
    }

    public void setWarnDate(String warnDate) {
        this.warnDate = warnDate;
    }

    public String getWarnContent() {
        return warnContent;
    }

    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent;
    }

    public String getWarnSovledDate() {
        return warnSovledDate;
    }

    public void setWarnSovledDate(String warnSovledDate) {
        this.warnSovledDate = warnSovledDate;
    }

    public int shuoldShow() {
        if (warnSovledDate.isEmpty()) {
            return 8;
        }
        return 0;
    }
}
