package com.example.coco.liveproject.bean;

/**
 * Created by coco on 2018/1/22.
 */

public class GiftMsgInfo {
    String avatar;
    String liveId;
    GiftInfo info;
    int giftNum;

    public GiftMsgInfo(String avatar, String liveId, GiftInfo info, int giftNum) {
        this.avatar = avatar;
        this.liveId = liveId;
        this.info = info;
        this.giftNum = giftNum;
    }

    public GiftMsgInfo() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public GiftInfo getInfo() {
        return info;
    }

    public void setInfo(GiftInfo info) {
        this.info = info;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }
}
