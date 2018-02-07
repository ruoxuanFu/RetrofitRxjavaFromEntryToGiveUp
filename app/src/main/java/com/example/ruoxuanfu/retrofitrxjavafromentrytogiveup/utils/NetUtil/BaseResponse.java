package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.NetUtil;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ruoxuan.fu on 2018/1/17.
 * <p>
 * Code is far away from bug with WOW protecting.
 * <p>
 * 把返回的details类作为泛型
 * list类型的返回可直接BaseResponse<ArrayList<E>>
 */

public class BaseResponse<T> {
    @SerializedName("code")
    public String code;
    @SerializedName("message")
    public String message;
    @SerializedName("details")
    public T details;

    /**
     * 判断code是否正确
     *
     * @return boolean
     */
    public boolean isSuccess() {
        if (code == null) {
            return false;
        }
        //添加正确的code
        return "100".equals(code);
    }
}
