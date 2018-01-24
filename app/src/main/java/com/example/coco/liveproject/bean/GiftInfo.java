package com.example.coco.liveproject.bean;

import com.example.coco.liveproject.R;

/**
 * Created by coco on 2018/1/18.
 */

public class GiftInfo {
    int giftId;

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    int resId;
    String giftName;
    int price;
    GiftType type;
    boolean isSelected;

    public static final GiftInfo gift = new GiftInfo(R.mipmap.xuegao, "雪糕", 1, GiftType.Repeat, false);
    public static final GiftInfo gift1 = new GiftInfo(R.mipmap.qishui, "汽水", 5, GiftType.Repeat, false);
    public static final GiftInfo gift2 = new GiftInfo(R.mipmap.juanzhi, "卷纸", 10, GiftType.Repeat, false);
    public static final GiftInfo gift3 = new GiftInfo(R.mipmap.zhiwu, "多肉植物", 20, GiftType.Repeat, false);
    public static final GiftInfo gift4 = new GiftInfo(R.mipmap.heifengli, "黑凤梨", 50, GiftType.Repeat, false);
    public static final GiftInfo gift5 = new GiftInfo(R.mipmap.yusan, "雨伞", 100, GiftType.Repeat, false);
    public static final GiftInfo gift6 = new GiftInfo(R.mipmap.shoubiao, "手表", 200, GiftType.Repeat, false);
    public static final GiftInfo gift7 = new GiftInfo(R.mipmap.youxiji, "游戏机", 300, GiftType.Repeat, false);
    public static final GiftInfo gift8 = new GiftInfo(R.mipmap.xiangji, "相机", 500, GiftType.Repeat, false);
    public static final GiftInfo gift9 = new GiftInfo(R.mipmap.chengbao, "城堡", 1000, GiftType.Repeat, false);
    public static final GiftInfo gift10 = new GiftInfo(R.mipmap.huojian, "火箭", 6666, GiftType.Repeat, false);
    public static final GiftInfo gift11 = new GiftInfo(R.mipmap.car, "超跑", 8888, GiftType.FullScreen, false);
    public static final GiftInfo giftEmpty = new GiftInfo(R.mipmap.wait, "敬请期待", 0, GiftType.Empty, false);

    public GiftInfo() {
    }

    public GiftInfo(int resId, String giftName, int price, GiftType type, boolean isSelected) {
        this.resId = resId;
        this.giftName = giftName;
        this.price = price;
        this.type = type;
        this.isSelected = isSelected;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public GiftType getType() {
        return type;
    }

    public void setType(GiftType type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    enum GiftType {
        //连发，全屏礼物
        Repeat, FullScreen, Empty;
    }
}
