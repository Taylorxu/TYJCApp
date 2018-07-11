package com.wise.www.basestone.view.network;


/**
 * Created by xu on 2018/2/5.
 */

public class ResultModel<Data> {

    private int Status;
    private String Reason;
    private Data rows;

    public Error getError() {
        return new Error(getStatus(), getReason());
    }


    public int getStatus() {
        return Status;
    }

    public Data getRows() {
        return rows;
    }

    public void setRows(Data rows) {
        this.rows = rows;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }
}
