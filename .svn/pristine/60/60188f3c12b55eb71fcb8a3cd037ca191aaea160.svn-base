package com.wise.www.basestone.view.network;

import java.io.IOException;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

public class FlatMapResponse<Data> implements Func1<Response<Data>, Observable<Data>> {
    @Override
    public Observable<Data> call(Response<Data> response) {
        if (response.isSuccessful()) {
            return Observable.just(response.body());
        } else {
            try {
                return Observable.error(new Error(response.code(), response.errorBody().string()));
            } catch (IOException e) {
                return Observable.error(new Error(response.code(), e.getMessage()));
            }
        }
    }
}
