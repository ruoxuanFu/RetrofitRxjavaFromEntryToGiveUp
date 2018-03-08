package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.bean.PersonalIndexStuResponse;
import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.bean.UserIdRequest;
import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil.BaseObserver;
import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil.BaseResponse;
import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil.NetConfig;
import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserIdRequest request = new UserIdRequest();
        String userId = "30911";
        request.setUserId(userId);

        RetrofitBuilder.getInstance(this).create(NetConfig.BASE_URL).getApiService()
                .toPersonalIndexStu(request)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PersonalIndexStuResponse>(this) {
                    @Override
                    protected void onRequestStart() {
                    }

                    @Override
                    protected void onRequestSuccess(PersonalIndexStuResponse value) {
                        Log.d("Fmsg", "detail is " + value.toString());
                    }

                    @Override
                    protected void onRequestError(String codeType, String msg) {
                        Log.d("Fmsg", "codeType is " + codeType);
                        Log.d("Fmsg", "msg is " + msg);
                    }

                    @Override
                    protected void onRequestComplete() {

                    }
                });
    }
}
