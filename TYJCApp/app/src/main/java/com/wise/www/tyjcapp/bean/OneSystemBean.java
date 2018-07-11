package com.wise.www.tyjcapp.bean;

/**
 * Created by Administrator on 2018/4/3.
 */

public class OneSystemBean {
    /**
     * "TradeVolume":交易量，
     * "TradeSucRate":成功率，
     * "TradeDynamicVolume":动账量，
     * "TradeStaticVolume":非动账量,
     * "TradeDynamicSucRate":动账成功率，
     * "TradeStaticSucRate":非动账成功率,
     * "DateTime":当前日期时间+星期几
     */

    public String TradeVolume;
    public String TradeSucRate;
    public String DateTime;
    public String TradeDynamicVolume;
    public String TradeStaticVolume;
    public String TradeDynamicSucRate;
    public String TradeStaticSucRate;

    public String getTradeVolume() {
        return TradeVolume;
    }

    public void setTradeVolume(String tradeVolume) {
        TradeVolume = tradeVolume;
    }

    public String getTradeSucRate() {
        return TradeSucRate;
    }

    public void setTradeSucRate(String tradeSucRate) {
        TradeSucRate = tradeSucRate;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getTradeDynamicVolume() {
        return TradeDynamicVolume;
    }

    public void setTradeDynamicVolume(String tradeDynamicVolume) {
        TradeDynamicVolume = tradeDynamicVolume;
    }

    public String getTradeStaticVolume() {
        return TradeStaticVolume;
    }

    public void setTradeStaticVolume(String tradeStaticVolume) {
        TradeStaticVolume = tradeStaticVolume;
    }

    public String getTradeDynamicSucRate() {
        return TradeDynamicSucRate;
    }

    public void setTradeDynamicSucRate(String tradeDynamicSucRate) {
        TradeDynamicSucRate = tradeDynamicSucRate;
    }

    public String getTradeStaticSucRate() {
        return TradeStaticSucRate;
    }

    public void setTradeStaticSucRate(String tradeStaticSucRate) {
        TradeStaticSucRate = tradeStaticSucRate;
    }
}
