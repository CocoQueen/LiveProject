package com.example.coco.liveproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.coco.liveproject.bean.GiftInfo;
import com.example.coco.liveproject.bean.GiftMsgInfo;
import com.example.coco.liveproject.widget.danmu.DanMuItemView;
import com.example.coco.liveproject.widget.gift.GiftFullView;
import com.example.coco.liveproject.widget.gift.GiftItem;
import com.example.coco.liveproject.widget.gift.GiftSendDialog;

/**
 * Created by coco on 2018/1/19.
 */

public class Test extends Activity {
    private static final int FIRST_GIFT_SEND_FLAG = -1;
    public static final int REPEAT_GIFT_SEND_FLAG =1 ;
    private int repeatTimeLimit=10;

    long firstSendTimeMillion;

    Handler repeatGiftTimer=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FIRST_GIFT_SEND_FLAG:
                    if (repeatTimeLimit>0){
                        repeatTimeLimit--;
                        sendEmptyMessageDelayed(FIRST_GIFT_SEND_FLAG,80);
                        giftSendDialog.setSendButtonText("发送（"+repeatTimeLimit+")");
                    }else{
                        giftItem.setIsRepeat(false);
                        firstSendTimeMillion=0;
                        repeatTimeLimit=10;
                        giftSendDialog.setSendButtonText("发送");
                    }

                    break;

                case REPEAT_GIFT_SEND_FLAG:


                    if (repeatTimeLimit>0){
                        repeatTimeLimit--;
                        sendEmptyMessageDelayed(REPEAT_GIFT_SEND_FLAG,80);
                        giftSendDialog.setSendButtonText("发送（"+repeatTimeLimit+")");

                    }else{
                        giftItem.setIsRepeat(false);
                        giftItem.repeatSendWithoutAddNum();
                        firstSendTimeMillion=0;
                        repeatTimeLimit=10;
                        giftSendDialog.setSendButtonText("发送");
                    }


                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }



        }
    };

    private DanMuItemView dd;
    private GiftItem giftItem;
    private GiftSendDialog giftSendDialog;
    private GiftFullView view;
    private GiftSendDialog.onGiftSendListener onGiftSendListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        giftItem = findViewById(R.id.giftItem);
        view = findViewById(R.id.giftFullView);
        view.setVisibility(View.INVISIBLE);
        FullGiftViewMatchParent();


        onGiftSendListener = new GiftSendDialog.onGiftSendListener() {
            @Override
            public void onSend(GiftInfo info) {

                GiftMsgInfo giftMsgInfo = new GiftMsgInfo();
                giftMsgInfo.setInfo(info);
               if (info.getType()==GiftInfo.GiftType.Repeat){
                   giftItem.bindData(giftMsgInfo);
                   sendGift();
               }
               else if (info.getType()==GiftInfo.GiftType.FullScreen){
                   view.showFullGift(giftMsgInfo);
               }



            }


        };
        giftSendDialog = new GiftSendDialog(this, R.style.custom_dialog, onGiftSendListener);
        giftSendDialog.show();


    }
    public void sendGift(){
        if (firstSendTimeMillion==0){
            giftItem.setIsRepeat(false);
            firstSendTimeMillion=System.currentTimeMillis();
            repeatGiftTimer.sendEmptyMessage(FIRST_GIFT_SEND_FLAG);
            giftItem.startAnimte();
        }
        else{
            giftItem.setIsRepeat(true);
            giftItem.repeatSend();
            repeatGiftTimer.removeMessages(FIRST_GIFT_SEND_FLAG);
            repeatGiftTimer.removeMessages(REPEAT_GIFT_SEND_FLAG);
            repeatGiftTimer.sendEmptyMessage(REPEAT_GIFT_SEND_FLAG);
            repeatTimeLimit=10;
        }
    }
    private void FullGiftViewMatchParent() {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        WindowManager wm = getWindowManager();
        Display defaultDisplay = wm.getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    public void showSendGiftDialog(View view) {
        giftSendDialog = new GiftSendDialog(this, R.style.custom_dialog,onGiftSendListener);
        giftSendDialog.show();
    }
}
