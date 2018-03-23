package com.wise.www.tyjcapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class SystemWorkingCaseBean implements Parcelable {

    private String TradeSysCode;
    private String TradeSysColour;
    private int TradeSysVolume;
    private String TradeSysName;
    private String TradeSysSucRate;
    /**
     * TradeBankName : 费县梁邹村镇银行股份有限公司
     * TradeBankCode : 320474200018
     */

    private String TradeBankName;
    private String TradeBankCode;


    protected SystemWorkingCaseBean(Parcel in) {
        TradeSysCode = in.readString();
        TradeSysColour = in.readString();
        TradeSysVolume = in.readInt();
        TradeSysName = in.readString();
        TradeBankName = in.readString();
        TradeBankCode = in.readString();
        TradeSysSucRate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TradeSysCode);
        dest.writeString(TradeSysColour);
        dest.writeInt(TradeSysVolume);
        dest.writeString(TradeSysName);
        dest.writeString(TradeBankName);
        dest.writeString(TradeBankCode);
        dest.writeString(TradeSysSucRate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SystemWorkingCaseBean> CREATOR = new Creator<SystemWorkingCaseBean>() {
        @Override
        public SystemWorkingCaseBean createFromParcel(Parcel in) {
            return new SystemWorkingCaseBean(in);
        }

        @Override
        public SystemWorkingCaseBean[] newArray(int size) {
            return new SystemWorkingCaseBean[size];
        }
    };

    public String getTradeSysCode() {
        return TradeSysCode;
    }

    public void setTradeSysCode(String tradeSysCode) {
        TradeSysCode = tradeSysCode;
    }

    public String getTradeSysColour() {
        return TradeSysColour;
    }

    public void setTradeSysColour(String tradeSysColour) {
        TradeSysColour = tradeSysColour;
    }

    public int getTradeSysVolume() {
        return TradeSysVolume;
    }

    public void setTradeSysVolume(int tradeSysVolume) {
        TradeSysVolume = tradeSysVolume;
    }

    public String getTradeSysName() {
        return TradeSysName;
    }

    public void setTradeSysName(String TradeSysName) {
        this.TradeSysName = TradeSysName;
    }

    public String getTradeBankName() {
        return TradeBankName;
    }

    public void setTradeBankName(String TradeBankName) {
        this.TradeBankName = TradeBankName;
    }

    public String getTradeBankCode() {
        return TradeBankCode;
    }

    public void setTradeBankCode(String TradeBankCode) {
        this.TradeBankCode = TradeBankCode;
    }

    public String getTradeSysSucRate() {
        return TradeSysSucRate;
    }

    public void setTradeSysSucRate(String tradeSysSucRate) {
        TradeSysSucRate = tradeSysSucRate;
    }
}
