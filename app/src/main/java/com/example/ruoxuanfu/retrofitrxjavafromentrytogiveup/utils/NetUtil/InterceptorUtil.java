package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ruoxuan.fu on 2018/2/7.
 * <p>
 * Code is far away from bug with WOW protecting.
 * <p>
 * 拦截器工具类
 */

public class InterceptorUtil {
    public static final String TAG = "InterceptorUtil";

    /**
     * 日志拦截器
     *
     * @return HttpLoggingInterceptor
     */
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, "log : " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * 添加其他拦截器
     *
     * @return Interceptor
     */
    public static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //在这里可以做一些事,比如token失效时,重新获取token,或者添加header,添加Cookie等等
                return chain.proceed(request);
            }
        };
    }
}
