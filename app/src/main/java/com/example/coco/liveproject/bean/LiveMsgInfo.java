package com.example.coco.liveproject.bean;

/**
 * Created by coco on 2018/1/16.
 */

public class LiveMsgInfo {
    int grade;
    String nickname;
    String text;
    String liveId;

    public LiveMsgInfo() {

    }

    public LiveMsgInfo(int grade, String nickname, String text, String liveId) {
        this.grade = grade;
        this.nickname = nickname;
        this.text = text;
        this.liveId = liveId;
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

    @Override
    public String toString() {
        return "TextMsgInfo{" +
                "grade=" + grade +
                ", nickname='" + nickname + '\'' +
                ", text='" + text + '\'' +
                ", adouID='" + liveId + '\'' +
                '}';
    }
}
