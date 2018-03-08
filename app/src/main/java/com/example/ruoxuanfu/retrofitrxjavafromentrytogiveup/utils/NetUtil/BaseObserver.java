package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by ruoxuan.fu on 2018/2/7.
 * <p>
 * Code is far away from bug with WOW protecting.
 */

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";

    private Context mContext;
    private Disposable mDisposable;

    protected abstract void onRequestStart();

    protected abstract void onRequestSuccess(T value);

    protected abstract void onRequestError(String codeType, String msg);

    protected abstract void onRequestComplete();

    public BaseObserver(Context context) {
        mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe");
        //添加订阅事件
        mDisposable = d;
        onRequestStart();
        switch (getNetStatus()) {
            case NET_NO:
                Log.d("TAG", "There is no network.");
                break;
            case NET_2G:
            case NET_3G:
            case NET_4G:
                Log.d("TAG", "There is on mobile network.");
                break;
            case NET_WIFI:
                Log.d("TAG", "There is on WIFI.");
                break;
            case NET_UNKNOWN:
                Log.d("TAG", "There is on unknown network.");
                break;
        }
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        Log.d(TAG, "onNext: code is " + baseResponse.code + ",message is " + baseResponse.message);
        if (baseResponse.isSuccess()) {
            onRequestSuccess(baseResponse.details);
        } else {
            onRequestError(baseResponse.code, baseResponse.message);
        }
    }

    @Override
    public void onError(Throwable e) {
        String type = e.toString();
        String msg = e.getMessage();
        Log.e(TAG, "onError: type is " + type + ",msg is " + msg, e);
        onRequestError(type, msg);
        onRequestComplete();
        setDisposableDis();
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        onRequestComplete();
        setDisposableDis();
    }

    /**
     * 解绑订阅事件
     */
    private void setDisposableDis() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private NetStateMonitor.NetState getNetStatus() {
        return NetStateMonitor.getInstance().getCurrentNetState(mContext);
    }
}
