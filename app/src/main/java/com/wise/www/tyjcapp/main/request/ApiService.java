package com.wise.www.tyjcapp.main.request;


import android.support.annotation.StringRes;

import com.wise.www.basestone.view.network.NetConfig;
import com.wise.www.basestone.view.network.ResultModel;
import com.wise.www.tyjcapp.bean.AlarmSystemBean;
import com.wise.www.tyjcapp.bean.MemberTradeBean;
import com.wise.www.tyjcapp.bean.SystemWorkingCaseBean;

import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


/**
 * soap 的请求接口发起
 * Created by xuzhiguang on 2018/2/1.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("{path}")
    Observable<Response<ResultModel<Void>>> userLoginServlet(@Path("path") String path, @FieldMap Map<String, Object> param);

    @FormUrlEncoded
    @POST("DictDataServlet")
    Observable<Response<ResultModel<List<SystemWorkingCaseBean>>>> dictDataServlet(@Field("key") String key);

    @FormUrlEncoded
    @POST("SystemStatusServlet")
    Observable<Response<ResultModel<List<SystemWorkingCaseBean>>>> systemStatusServlet(@Field("tradeBankCode") String tradeBankCode);

    @FormUrlEncoded
    @POST("CurrentAlarmServlet")
    Observable<Response<ResultModel<List<AlarmSystemBean>>>> currentAlarmServlet(@Field("page") String page);

    @FormUrlEncoded
    @POST("HistoryAlarmServlet")
    Observable<Response<ResultModel<List<AlarmSystemBean>>>> historyAlarmServlet(@Field("page") int page);


    @POST("TradeBankTopServlet")
    Observable<Response<ResultModel<List<MemberTradeBean>>>> tradeBankTopServlet();


    class Creator {
        private static ApiService apiService;

        public static ApiService get() {
            if (apiService == null) {
                create();
            }
            return apiService;
        }

        private static synchronized void create() {
            if (apiService == null) {
                apiService = getRetrofit().create(ApiService.class);
            }
        }

        private static Retrofit getRetrofit() {
            return new Retrofit.Builder()
                    .baseUrl(BaseUrl.Host)
                    .client(NetConfig.getInstance().getClient())
                    .addConverterFactory(GsonConverterFactory.create(NetConfig.getInstance().getGson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

}
