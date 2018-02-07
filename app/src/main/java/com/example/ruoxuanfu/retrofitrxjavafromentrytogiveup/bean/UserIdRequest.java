package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.bean;


import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.DESUtils;

/**
 * Created by ruoxuan.fu on 2018/1/19.
 * <p>
 * Code is far away from bug with WOW protecting.
 * <p>
 * 只需要userId的请求
 */

public class UserIdRequest {
    private String userId;

    public String getUserId() {
        return DESUtils.decrypt3DES(userId);
    }

    public void setUserId(String userId) {
        this.userId = DESUtils.encrypt3DES(userId);
    }
}
