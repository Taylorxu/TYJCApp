package com.wise.www.basestone.view.network;

import rx.Observable;
import rx.functions.Func1;


public class FlatMapTopRes<Data> implements Func1<ResultModel<Data>, Observable<Data>> {
    @Override
    public Observable<Data> call(ResultModel<Data> response) {
        if (response.getStatus()==0) {
            return Observable.just(response.getRows());
        } else {
            return Observable.error(response.getError());
        }

    }
}
