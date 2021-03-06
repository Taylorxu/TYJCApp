package com.wise.www.basestone.view.network;



import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;


public class Notification {

    private static Observable<Notification> observable;

    public static Subscription register(Action1<Notification> action1) {
        if (observable == null) {
            observable = RxBus.getDefault().toObservable(Notification.class);
        }
        return observable.subscribe(action1, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


    private int code;
    private long id;
    private Object extra;

    public Notification(int code, long id) {
        this.code = code;
        this.id = id;
    }

    /**
     * 001  top 排名跳转到首页
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getExtra() {
        return extra;
    }

    public Notification setExtra(Object extra) {
        this.extra = extra;
        return this;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "code=" + code +
                ", id=" + id +
                '}';
    }
}
