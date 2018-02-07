package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by ruoxuan.fu on 2018/2/7.
 * <p>
 * Code is far away from bug with WOW protecting.
 * <p>
 * 管理网络状态
 */

public class NetStateMonitor {

    private Subject<Object> mObjectSubject;
    private NetStatusReceiver mNetStatusReceiver;
    private volatile static NetStateMonitor mInstance;              //volatile保持并发的可见性

    private NetStateMonitor() {
    }

    public static NetStateMonitor getInstance() {
        if (mInstance == null) {
            synchronized (NetStateMonitor.class) {
                if (mInstance == null) {
                    mInstance = new NetStateMonitor();
                }
            }
        }
        return mInstance;
    }

    /**
     * 绑定观察
     *
     * @param context context
     * @return NetState
     */
    public Observable<NetState> observ(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context can not be null!");
        }
        if (mObjectSubject == null) {
            mObjectSubject = PublishSubject.create().toSerialized();            //toSerialized保证线程安全
        }
        //注册网络监听广播
        registerReceiver(context, mObjectSubject);
        return mObjectSubject.cast(NetState.class);
    }

    /**
     * 解绑观察
     *
     * @param context    context
     * @param disposable 绑定持有
     */
    public void dispose(Context context, Disposable disposable) {
        if (context == null) {
            throw new IllegalArgumentException("Context can not be null!");
        }
        if (disposable == null) {
            throw new IllegalArgumentException("Context can not be null!");
        }
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        if (!mObjectSubject.hasObservers()) {
            unregisterReceiver(context);
        }
    }

    /**
     * 注册广播
     *
     * @param context context
     * @param subject subject
     */
    private void registerReceiver(Context context, Subject<Object> subject) {
        if (mNetStatusReceiver == null) {
            mNetStatusReceiver = new NetStatusReceiver(subject);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.getApplicationContext().registerReceiver(mNetStatusReceiver, intentFilter);
        }
    }

    private void unregisterReceiver(Context context) {
        if (mNetStatusReceiver != null) {
            context.getApplicationContext().unregisterReceiver(mNetStatusReceiver);
            mNetStatusReceiver = null;
        }
    }

    /**
     * 获取当前网络状态
     *
     * @param context context
     * @return NetState
     */
    public NetState getCurrentNetState(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context can not be null!");
        }
        NetState netState;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    netState = NetState.NET_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (networkInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            netState = NetState.NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            netState = NetState.NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            netState = NetState.NET_4G;
                            break;
                        default:
                            netState = NetState.NET_UNKNOWN;
                    }
                    break;
                default:
                    netState = NetState.NET_UNKNOWN;
                    break;
            }
        } else {
            netState = NetState.NET_NO;
        }
        return netState;
    }

    private class NetStatusReceiver extends BroadcastReceiver {
        private Subject<Object> mObjectSubject;

        public NetStatusReceiver(Subject<Object> objectSubject) {
            mObjectSubject = objectSubject;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
                //监听网络变化，发送消息
                mObjectSubject.onNext(getCurrentNetState(context));
            }
        }
    }

    /**
     * 网络状态
     */
    public enum NetState {
        NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN
    }
}
