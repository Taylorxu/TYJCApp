package com.wise.www.tyjcapp.bean;

/**
 * Created by Administrator on 2018/4/3.
 */

public class WrapOnSystemBean {
    private OneSystemBean current;
    private OneSystemBean history;

    public OneSystemBean getCurrent() {
        return current;
    }

    public void setCurrent(OneSystemBean current) {
        this.current = current;
    }

    public OneSystemBean getHistory() {
        return history;
    }

    public void setHistory(OneSystemBean history) {
        this.history = history;
    }
}
