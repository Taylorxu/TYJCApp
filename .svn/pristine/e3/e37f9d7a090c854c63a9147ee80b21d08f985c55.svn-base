package com.wise.www.basestone.view.network;

import rx.Observable;
import rx.functions.Func1;

/**
 *
 * @param <Data>
 */
public class FlatMapTopResList<Data> implements Func1<ResultModelData<Data>, Observable<Data>> {
    @Override
    public Observable<Data> call(ResultModelData<Data> response) {
        if (response.getReturnState().equals("0")) {
            return Observable.just(response.getReturnValue());
        } else {
            return Observable.error(response.getError());
        }

    }
}
