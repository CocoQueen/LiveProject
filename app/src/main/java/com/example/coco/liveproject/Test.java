package com.example.coco.liveproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.coco.liveproject.widget.gift.GiftItem;

/**
 * Created by coco on 2018/1/19.
 */

public class Test extends Activity {
    private static final  int FIRST_SEND_GIFT=-1;
    private static final  int REPEAT_SEND_GIFT=1;
    private int repeatTimeLimit=10;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case FIRST_SEND_GIFT:
                    if (repeatTimeLimit>0){
                        repeatTimeLimit--;
                        sendEmptyMessageDelayed(FIRST_SEND_GIFT,80);
                        //TODO 点击按钮发送礼物
                        mBtn.setText("发送"+repeatTimeLimit);
                    }else {
                        item.setIsRepeat(false);
                        firstSendTime = 0;
                        repeatTimeLimit=10;
                        //TODO mbtn
                        mBtn.setText("发送");
                    }
                    break;
                case REPEAT_SEND_GIFT:
                    if (repeatTimeLimit>0){
                        repeatTimeLimit--;
                        sendEmptyMessageDelayed(REPEAT_SEND_GIFT,80);
                        //TODO mbtn
                        mBtn.setText("发送"+repeatTimeLimit);
                    }else {
                        item.setIsRepeat(false);
                        item.repeatSendWithoutAddNum();
                        firstSendTime=0;
                        repeatTimeLimit=10;
                        //TODO mbtn
                        mBtn.setText("发送");
                    }
                    break;
                    default:
                        super.handleMessage(msg);
                        break;
            }
        }
    };
    private GiftItem item;
    private long firstSendTime;
    private Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
//
//        GiftSendDialog dialog = new GiftSendDialog(this, R.style.custom_dialog);
//        dialog.show();

        item = findViewById(R.id.giftItem);
//        item.startAnimtion();

        mBtn = findViewById(R.id.mBtn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstSendTime==0){
                    item.setIsRepeat(false);
                    firstSendTime=System.currentTimeMillis();
                    handler.sendEmptyMessage(FIRST_SEND_GIFT);
                    item.startAnimtion();
                }else {
                    item.setIsRepeat(true);
                    item.repeatSend();
                    handler.removeMessages(FIRST_SEND_GIFT);
                    handler.removeMessages(REPEAT_SEND_GIFT);
                    handler.sendEmptyMessage(REPEAT_SEND_GIFT);
                    repeatTimeLimit=10;
                }
            }
        });
    }
}
