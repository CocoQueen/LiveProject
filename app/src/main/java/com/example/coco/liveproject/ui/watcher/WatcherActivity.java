package com.example.coco.liveproject.ui.watcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.app.LiveApplication;
import com.example.coco.liveproject.bean.DMMsgInfo;
import com.example.coco.liveproject.bean.LiveMsgInfo;
import com.example.coco.liveproject.custom.ProfileInfoCustom;
import com.example.coco.liveproject.model.MessageObservable;
import com.example.coco.liveproject.model.live.Constants;
import com.example.coco.liveproject.model.live.DemoFunc;
import com.example.coco.liveproject.utils.ToastUtils;
import com.example.coco.liveproject.widget.BottomChatLayout;
import com.example.coco.liveproject.widget.BottomSwichLayout;
import com.example.coco.liveproject.widget.DanmuView;
import com.example.coco.liveproject.widget.HeightRelativeLayout;
import com.example.coco.liveproject.widget.LiveMsgListView;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.livesdk.ILVText;

import java.util.ArrayList;
import java.util.List;

public class WatcherActivity extends AppCompatActivity implements ILVLiveConfig.ILVLiveMsgListener {
    private AVRootView mAv_watcher;
    private int roomId;
    private String hostId;
    private BottomSwichLayout bsl;
    private BottomChatLayout bcl;
    private LiveMsgListView lmlv;
    ArrayList<LiveMsgInfo> list = new ArrayList<>();
    private HeightRelativeLayout mHrl;
    DanmuView danmuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher);

        MessageObservable.getInstance().addObserver(this);
        initView();
        setDefaultStatus();
        lmlv.setData(list);
        initListener();
        initRootView();
        getSomething();
    }

    private void setDefaultStatus() {
        bcl.setVisibility(View.INVISIBLE);
        bsl.setVisibility(View.VISIBLE);

    }

    private void initRootView() {
        ILVLiveManager.getInstance().setAvVideoView(mAv_watcher);
    }

    private void initListener() {
        mHrl.setOnHeightRelativeLayoutChangedListener(new HeightRelativeLayout.onHeightRelativeLayoutChangedListener() {
            @Override
            public void showNormal() {
                setDefaultStatus();
            }

            @Override
            public void showChat() {
                bcl.setVisibility(View.VISIBLE);
                bsl.setVisibility(View.INVISIBLE);

            }
        });
        bsl.setOnSwichLayoutListener(new BottomSwichLayout.onSwichLayoutListener() {
            @Override
            public void onChat() {
               bcl.setVisibility(View.VISIBLE);
               bsl.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onClose() {
                finish();
            }
        });
        bcl.setOnChatSendMsgListener(new BottomChatLayout.onChatSendMsgListener() {
            @Override
            public void sendMsg(String msg) {
                sendTextMsg(msg, ProfileInfoCustom.TEXT_MSG);
            }

            @Override
            public void sendDanMu(String msg) {
                String newmsg =ProfileInfoCustom.TYPE_DAN+msg;
                sendTextMsg(newmsg,ProfileInfoCustom.DANMU_MSG);

            }
        });
    }

    private void sendTextMsg(final String msg, final int options) {
        List<String> ids = new ArrayList<>();
        ids.add(hostId);
        TIMFriendshipManager.getInstance().getFriendsProfile(ids, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                realSend(timUserProfiles, msg,options);
            }
        });
    }

    private void realSend(List<TIMUserProfile> timUserProfiles, final String msg, final int options) {
        final TIMUserProfile profile = timUserProfiles.get(0);

        ILVLiveManager.getInstance().sendText(new ILVText(ILVText.ILVTextType.eGroupMsg, profile.getIdentifier(), msg), new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                String grade;
                LiveMsgInfo info = new LiveMsgInfo();
                byte[] bytes = profile.getCustomInfo().get(ProfileInfoCustom.INFO_GRADE);
                if (bytes != null) {
                    grade = new String(bytes);
                } else {
                    grade = "0";
                }
                String identifier = LiveApplication.getApp().getUserProfile().getProfile().getIdentifier();
                info.setLiveId(identifier);
                info.setGrade(Integer.parseInt(grade));
                info.setText(msg);
                info.setNickname(profile.getNickName());

                if (options==ProfileInfoCustom.DANMU_MSG){
                    //发弹幕
                    String newmsg = msg.substring(ProfileInfoCustom.TYPE_DAN.length(), msg.length());
                    String headImg = LiveApplication.getApp().getUserProfile().getProfile().getFaceUrl();
                    DMMsgInfo dmMsgInfo=new DMMsgInfo();
                    dmMsgInfo.setLiveId(hostId);
                    dmMsgInfo.setAvatar(headImg);
                    dmMsgInfo.setGrade(Integer.parseInt(grade));
                    dmMsgInfo.setText(newmsg);

                    danmuView.addDanMu(dmMsgInfo);
                    info.setText(newmsg);
                }
                lmlv.addMsg(info);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtils.show("发送失败..." + errMsg + errCode);

            }
        });
    }

    private void getSomething() {
        Intent intent = getIntent();
        if (intent != null) {

            roomId = intent.getIntExtra("roomId", -1);
            hostId = intent.getStringExtra("hostId");
            watcher(roomId + "");
        }
    }

    private void watcher(String id) {
        int roomId = DemoFunc.getIntValue(id, -1);
        if (-1 == roomId) {
            ToastUtils.show("房间号不合法");
            finish();
            return;
        }
        ILVLiveRoomOption option = new ILVLiveRoomOption("")
                .controlRole(Constants.ROLE_GUEST) //是一个浏览者
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .autoCamera(false)
                .autoMic(false);
        ILVLiveManager.getInstance().joinRoom(roomId,
                option, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        //成功的时候怎么办

                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtils.show("加入房间失败，正在退出...");
                        //退出房间
                        finish();
                    }
                });

    }

    private void initView() {
        mAv_watcher = findViewById(R.id.mAv_watcher);
        bsl = findViewById(R.id.bsl);
        bcl = findViewById(R.id.bcl);
        lmlv = findViewById(R.id.lmlv);
        mHrl = findViewById(R.id.mHrl);
        danmuView=findViewById(R.id.mDv_watch);


    }

    @Override
    protected void onPause() {
        super.onPause();
        ILVLiveManager.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILVLiveManager.getInstance().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        quitRoom();
        ILVLiveManager.getInstance().onDestory();
    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        LiveMsgInfo liveMsgInfo=new LiveMsgInfo();
        String msg = text.getText();
        String nickName = userProfile.getNickName();
        String grade;
        byte[] bytes = userProfile.getCustomInfo().get(ProfileInfoCustom.INFO_GRADE);
        if (bytes!=null){
            grade=new String(bytes);
        }
        else {
            grade="0";
        }
       liveMsgInfo.setLiveId(SenderId);
        liveMsgInfo.setGrade(Integer.parseInt(grade));
        if (msg.startsWith(ProfileInfoCustom.TYPE_DAN)){
            String newmsg = msg.substring(ProfileInfoCustom.TYPE_DAN.length(), msg.length());
            liveMsgInfo.setText(newmsg);
            String headImg = userProfile.getFaceUrl();
            DMMsgInfo info=new DMMsgInfo();
            info.setAvatar(headImg);
            info.setText(newmsg);
            info.setGrade(Integer.parseInt(grade));
            info.setLiveId(SenderId);
            danmuView.addDanMu(info);

        }else{
            liveMsgInfo.setText(msg);
        }
        lmlv.addMsg(liveMsgInfo);

    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }
    public void quitRoom(){
        ILVLiveManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                ToastUtils.show("退出房间成功");
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        });
    }
}
