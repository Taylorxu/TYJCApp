package com.wise.www.tyjcapp.bean;

/**
 * Created by Administrator on 2018/3/22.
 */

public class AlarmSystemBean {

    /**
     * RecoveryTime : 2018-02-08 10:06:00
     * AlarmTime : 2018-03-22 14:22:20
     * AlarmContent : 【IGA服务器】在【00:00:32至23:59:32】的时间里【1分钟】内无成功交易
     */

    private String RecoveryTime;
    private String AlarmTime;
    private String AlarmContent;

    public String getRecoveryTime() {
        return RecoveryTime;
    }

    public void setRecoveryTime(String RecoveryTime) {
        this.RecoveryTime = RecoveryTime;
    }

    public String getAlarmTime() {
        return AlarmTime;
    }

    public void setAlarmTime(String AlarmTime) {
        this.AlarmTime = AlarmTime;
    }

    public String getAlarmContent() {
        return AlarmContent;
    }

    public void setAlarmContent(String AlarmContent) {
        this.AlarmContent = AlarmContent;
    }

    public int shuoldShow() {
        if (RecoveryTime==null) {
            return 8;
        }
        return 0;
    }
}
