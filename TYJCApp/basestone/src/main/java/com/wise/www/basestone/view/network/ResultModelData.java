package com.wise.www.basestone.view.network;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */

public class ResultModelData<Data1> {

    private String returnMsg;
    private Data1 returnValue;
    private String returnState;

    public static class ReturnValueBean <Data>{
        private String currentPage;
        private int totalPage;
        private List<Data> resultList;

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<Data> getResultList() {
            return resultList;
        }

        public void setResultList(List<Data> resultList) {
            this.resultList = resultList;
        }
    }


    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Data1 getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Data1 returnValue) {
        this.returnValue = returnValue;
    }

    public String getReturnState() {
        return returnState;
    }

    public void setReturnState(String returnState) {
        this.returnState = returnState;
    }


    public Error getError() {
        return new Error(Integer.decode(getReturnState()), returnMsg);
    }

}
