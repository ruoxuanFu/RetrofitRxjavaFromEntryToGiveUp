package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ruoxuan.fu on 2018/2/7.
 * <p>
 * Code is far away from bug with WOW protecting.
 */

public class RetrofitBuilder {
    private static RetrofitBuilder mRetrofitBuilder;
    private static ApiService mApiService;

    private RetrofitBuilder() {
    }

    public static RetrofitBuilder getInstance() {
        if (mRetrofitBuilder == null) {
            synchronized (RetrofitBuilder.class) {
                if (mRetrofitBuilder == null) {
                    mRetrofitBuilder = new RetrofitBuilder();
                }
            }
        }
        return mRetrofitBuilder;
    }

    public RetrofitBuilder create(String url) {
        //OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(NetConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(NetConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(NetConfig.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.HeaderInterceptor())
                .addInterceptor(InterceptorUtil.LogInterceptor())
                .build();
        //Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        mApiService = retrofit.create(ApiService.class);
        return this;
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
