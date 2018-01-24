package com.example.coco.liveproject.widget.gift;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.GiftMsgInfo;
import com.example.coco.liveproject.widget.danmu.DanMuItemView;

import java.util.LinkedList;

/**
 * Created by coco on 2018/1/18.
 */

public class GiftView extends LinearLayout {


    private LayoutInflater inflater;
    private GiftItem mGift_item;
    private GiftItem mGift_item2;
    private int screenWidth;
    LinkedList<GiftMsgInfo> list;

    public GiftView(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public GiftView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.gift_view, this, true);
        mGift_item = view.findViewById(R.id.mGift_item);
        mGift_item2 = view.findViewById(R.id.mGift_item2);
        setDefault();

    }

    private void setDefault() {
        mGift_item.setVisibility(INVISIBLE);
        mGift_item2.setVisibility(INVISIBLE);
    }

    public GiftItem getItemView() {
        if (mGift_item.getVisibility() == INVISIBLE) {
            return mGift_item;
        } else if (mGift_item2.getVisibility() == INVISIBLE) {
            return mGift_item2;
        } else {
            return null;
        }
    }

    public void addGift(GiftMsgInfo info,onGiftViewListener listener) {
        GiftItem view = getItemView();
        if (view != null) {
            if (listener!=null){
            listener.onSetListener(view);
            }
        } else {

            if (list == null) {
                list = new LinkedList<>();
            }
            list.add(info);
        }
    }



    private void showCacheMsg(DanMuItemView view) {
        if (list != null && !list.isEmpty()) {
            GiftMsgInfo info = list.removeFirst();
//            startActionAnim(info, view);
        }


    }
    public interface onGiftViewListener{
        void onSetListener(GiftItem item);
    }
}
