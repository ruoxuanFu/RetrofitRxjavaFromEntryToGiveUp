package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil;

import android.content.Context;

import java.io.InputStream;
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
    private static Context mContext;
    private SSLTool.SSLParams mSSLParams;

    private RetrofitBuilder(Context context) {
        mContext = context.getApplicationContext();
    }

    public static RetrofitBuilder getInstance(Context context) {
        if (mRetrofitBuilder == null) {
            synchronized (RetrofitBuilder.class) {
                if (mRetrofitBuilder == null) {
                    mRetrofitBuilder = new RetrofitBuilder(context);
                }
            }
        }
        return mRetrofitBuilder;
    }

    public RetrofitBuilder create(String url) {
        if (mApiService == null) {
            //OKHttpClient对象
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            if (mSSLParams != null) {
                okHttpClientBuilder.sslSocketFactory(mSSLParams.sslSocketFactory, mSSLParams.x509TrustManager);
            }
            okHttpClientBuilder.connectTimeout(NetConfig.HTTP_TIME, TimeUnit.SECONDS)
                    .readTimeout(NetConfig.HTTP_TIME, TimeUnit.SECONDS)
                    .writeTimeout(NetConfig.HTTP_TIME, TimeUnit.SECONDS)
                    .addInterceptor(InterceptorUtil.HeaderInterceptor())
                    .addInterceptor(InterceptorUtil.LogInterceptor());
            //Retrofit对象
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
            mApiService = retrofit.create(ApiService.class);
        }
        return this;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    /**
     * 添加https证书
     *
     * @param certificates 地址
     *                     eg.:new InputStream[]{
     *                     context.getResources().openRawResource(R.raw.networklib_pre),
     *                     context.getResources().openRawResource(R.raw.networklib_mp),
     *                     context.getResources().openRawResource(R.raw.newworklib_test)
     *                     }
     */
    public void addSSL(InputStream[] certificates) {
        mSSLParams = SSLTool.getSslSocketFactory(certificates, null, null);
    }
}
