package com.example.coco.liveproject.bean;

import com.tencent.TIMUserProfile;

/**
 * Created by coco on 2018/1/3.
 */

public class UserProfile {
    int fork;
    int fans;
    int send;
    int receive;
    int grade;
    String xingzuo;
    TIMUserProfile profile;

    public int getFork() {
        return fork;
    }

    public void setFork(int fork) {
        this.fork = fork;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getSend() {
        return send;
    }

    public void setSend(int send) {
        this.send = send;
    }

    public int getReceive() {
        return receive;
    }

    public void setReceive(int receive) {
        this.receive = receive;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getXingzuo() {
        return xingzuo;
    }

    public void setXingzuo(String xingzuo) {
        this.xingzuo = xingzuo;
    }

    public TIMUserProfile getProfile() {
        return profile;
    }

    public void setProfile(TIMUserProfile profile) {
        this.profile = profile;
    }
}
