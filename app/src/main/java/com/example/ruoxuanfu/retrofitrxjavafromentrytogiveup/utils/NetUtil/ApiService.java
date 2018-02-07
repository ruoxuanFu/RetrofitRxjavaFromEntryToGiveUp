package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil;

import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.bean.PersonalIndexStuResponse;
import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.bean.UserIdRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ruoxuan.fu on 2018/2/7.
 * <p>
 * Code is far away from bug with WOW protecting.
 * <p>
 * 网络接口类
 */

public interface ApiService {

    @POST("personal/toPersonalIndexStu")
    Observable<BaseResponse<PersonalIndexStuResponse>> toPersonalIndexStu(@Body UserIdRequest request);
}
