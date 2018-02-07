package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.bean;


import com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils.DESUtils;

/**
 * Created by ruoxuan.fu on 2018/1/19.
 * <p>
 * Code is far away from bug with WOW protecting.
 * <p>
 * 个人中心首页
 */

public class PersonalIndexStuResponse {

    private String userId;
    private String name;
    private String sex;
    private String iconUrl;                                 //头像url
    private String updateComments;                          //个性签名
    private int usedTime;                                   //已使用天数
    private int totalTime;                                  //总天数
    private int usableTime;                                 //可用天数

    public String getUserId() {
        return DESUtils.decrypt3DES(userId);
    }

    public void setUserId(String userId) {
        this.userId = DESUtils.encrypt3DES(userId);
    }

    public String getName() {
        if (name != null) {
            return DESUtils.decrypt3DES(name);
        } else {
            return null;
        }
    }

    public void setName(String name) {
        this.name = DESUtils.encrypt3DES(name);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUpdateComments() {
        return updateComments;
    }

    public void setUpdateComments(String updateComments) {
        this.updateComments = updateComments;
    }

    public int getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getUsableTime() {
        return usableTime;
    }

    public void setUsableTime(int usableTime) {
        this.usableTime = usableTime;
    }

    @Override
    public String toString() {
        return "PersonalIndexStuResponse{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", sex='" + sex + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", updateComments='" + updateComments + '\'' +
                ", usedTime=" + usedTime +
                ", totalTime=" + totalTime +
                ", usableTime=" + usableTime +
                '}';
    }
}
