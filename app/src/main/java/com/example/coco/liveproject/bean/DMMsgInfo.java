package com.example.coco.liveproject.bean;

/**
 * Created by coco on 2018/1/17.
 */

public class DMMsgInfo {
    String avatar;
    int grade;
    String nickname;
    String text;
    String liveId;

    public DMMsgInfo() {
    }

    public DMMsgInfo(String avatar, int grade, String nickname, String text, String liveId) {
        this.avatar = avatar;
        this.grade = grade;
        this.nickname = nickname;
        this.text = text;
        this.liveId = liveId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }
}
