package com.example.coco.liveproject.ui.host;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.app.LiveApplication;
import com.example.coco.liveproject.bean.DMMsgInfo;
import com.example.coco.liveproject.bean.LiveMsgInfo;
import com.example.coco.liveproject.custom.ProfileInfoCustom;
import com.example.coco.liveproject.model.MessageObservable;
import com.example.coco.liveproject.utils.ToastUtils;
import com.example.coco.liveproject.widget.widget.BottomChatLayout;
import com.example.coco.liveproject.widget.widget.BottomSwichLayout;
import com.example.coco.liveproject.widget.danmu.DanmuView;
import com.example.coco.liveproject.widget.widget.HeightRelativeLayout;
import com.example.coco.liveproject.widget.widget.LiveMsgListView;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVText;

import java.util.ArrayList;
import java.util.List;

public class HostLiveActivity extends AppCompatActivity implements HostLiveContract.HostLiveView, ILVLiveConfig.ILVLiveMsgListener {

    private Toolbar mTool_host;
    private AVRootView mAv_room;

    private int roomId;
    private HostLiveContract.HostLivePresenter presenter;
    private BottomSwichLayout mBsl_host_live;
    private BottomChatLayout mBcl_host_live;
    private LiveMsgListView mLmlv_host_live;
    private HeightRelativeLayout mHcl_host_live;
    ArrayList<LiveMsgInfo> list = new ArrayList<>();
    private String sendserId;
    private LiveMsgInfo info;
    private DanmuView danmuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_live);

        MessageObservable.getInstance().addObserver(this);
        initView();
        mLmlv_host_live.setData(list);
        setBottomSwichListener();
        setDefaultStatus();
        initToolbar();
        initListener();
        initPresenter();
        initCreateHost();
    }

    private void setDefaultStatus() {
        mBcl_host_live.setVisibility(View.INVISIBLE);
        mBsl_host_live.setVisibility(View.VISIBLE);
    }

    private void setBottomSwichListener() {
        mBsl_host_live.setOnSwichLayoutListener(new BottomSwichLayout.onSwichLayoutListener() {
            @Override
            public void onChat() {
                mBcl_host_live.setVisibility(View.VISIBLE);
                mBsl_host_live.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onClose() {
                finish();
            }

            @Override
            public void onGift() {
            }
        });
    }

    private void initCreateHost() {
        Intent intent = getIntent();
        if (intent != null) {
            roomId = intent.getIntExtra("roomId", -1);
        }
        presenter.createHost(roomId);
    }

    private void initPresenter() {
        this.presenter = new HostLivePresenterImpl(this);
    }

    private void initListener() {
        mHcl_host_live.setOnHeightRelativeLayoutChangedListener(new HeightRelativeLayout.onHeightRelativeLayoutChangedListener() {
            @Override
            public void showNormal() {
                setDefaultStatus();
            }

            @Override
            public void showChat() {
                mBcl_host_live.setVisibility(View.VISIBLE);
                mBsl_host_live.setVisibility(View.INVISIBLE);
            }
        });
        mBcl_host_live.setOnChatSendMsgListener(new BottomChatLayout.onChatSendMsgListener() {
            @Override
            public void sendMsg(String msg) {
                if (TextUtils.isEmpty(sendserId)) {
                    sendserId = LiveApplication.getApp().getUserProfile().getProfile().getIdentifier();
                }
                sendTextMsg(msg, sendserId, ProfileInfoCustom.TEXT_MSG);
            }

            @Override
            public void sendDanMu(String msg) {
                if (TextUtils.isEmpty(sendserId)) {
                    sendserId = LiveApplication.getApp().getUserProfile().getProfile().getIdentifier();
                }
                String newMsg = ProfileInfoCustom.TYPE_DAN + msg;
                sendTextMsg(newMsg, sendserId, ProfileInfoCustom.DANMU_MSG);

            }
        });
    }

    private void sendTextMsg(final String msg, String id, final int options) {
        List<String> ids = new ArrayList<>();
        ids.add(id);
        TIMFriendshipManager.getInstance().getFriendsProfile(ids, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                realSend(timUserProfiles, msg, options);

            }
        });

    }

    private void realSend(List<TIMUserProfile> timUserProfiles, final String msg, final int options) {
        final TIMUserProfile profile = timUserProfiles.get(0);
        ILVLiveManager.getInstance().sendText(new ILVText(ILVText.ILVTextType.eGroupMsg, profile.getIdentifier(), msg), new ILiveCallBack() {

            @Override
            public void onSuccess(Object data) {
                String grade = null;
                String liveId;
                String nickName;
                LiveMsgInfo info = new LiveMsgInfo();
                if (sendserId.equals(LiveApplication.getApp().getUserProfile().getProfile().getIdentifier())) {
                    grade = LiveApplication.getApp().getUserProfile().getGrade() + "";
                    liveId = LiveApplication.getApp().getUserProfile().getProfile().getIdentifier();
                    nickName = LiveApplication.getApp().getUserProfile().getProfile().getNickName();
                } else {
                    byte[] bytes = profile.getCustomInfo().get(ProfileInfoCustom.INFO_GRADE);
                    if (bytes != null) {
                        grade = new String(bytes);
                    }
                    nickName = profile.getNickName();
                }
                if (TextUtils.isEmpty(grade)) {
                    grade = "0";
                }
                liveId = LiveApplication.getApp().getUserProfile().getProfile().getIdentifier();
                info.setGrade(Integer.parseInt(grade));
                info.setText(msg);
                info.setNickname(TextUtils.isEmpty(nickName) ? sendserId : nickName);
                info.setLiveId(liveId);
                if (options == ProfileInfoCustom.DANMU_MSG) {
                    String newMsg = msg.substring(ProfileInfoCustom.TYPE_DAN.length(), msg.length());
                    String headImg = LiveApplication.getApp().getUserProfile().getProfile().getFaceUrl();
                    DMMsgInfo dmMsgInfo = new DMMsgInfo();
                    dmMsgInfo.setLiveId(liveId);
                    dmMsgInfo.setAvatar(headImg);
                    dmMsgInfo.setGrade(Integer.parseInt(grade));
                    dmMsgInfo.setText(newMsg);
                    danmuView.addDanMu(dmMsgInfo);
                    info.setText(newMsg);


                }

                mLmlv_host_live.addMsg(info);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtils.show("发送失败" + errMsg + errCode);

            }
        });
    }

    private void initToolbar() {
        mTool_host.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.quitHost(roomId);
            }
        });
    }

    private void initView() {
        mTool_host = findViewById(R.id.mTool_host);
        mAv_room = findViewById(R.id.mAv_room);

        mHcl_host_live = findViewById(R.id.mHcl_host_live);
        mBsl_host_live = findViewById(R.id.mBsl_host_live);
        mBcl_host_live = findViewById(R.id.mBcl_host_live);
        mLmlv_host_live = findViewById(R.id.mLmlv_host_live);
        danmuView = findViewById(R.id.mDv);

        mBsl_host_live.mImg_bsl_gift.setVisibility(View.INVISIBLE);
        ILVLiveManager.getInstance().setAvVideoView(mAv_room);//添加avrootview
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

    private void quitRoom() {
        ILVLiveManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                ToastUtils.show("退出直播成功");
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtils.show("退出直播失败" + errMsg + errCode);

            }
        });
    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        sendserId = SenderId;
        String msg = text.getText();
        String nickName = userProfile.getNickName();
        String grade;
        byte[] bytes = userProfile.getCustomInfo().get(ProfileInfoCustom.INFO_GRADE);
        if (bytes != null) {
            grade = new String(bytes);
        } else {
            grade = "0";
        }
        if (msg.startsWith(ProfileInfoCustom.TYPE_DAN)) {
            String newMsg = msg.substring(ProfileInfoCustom.TYPE_DAN.length(), msg.length());
            info = new LiveMsgInfo(Integer.parseInt(grade), nickName, msg, SenderId);
            String headImg = userProfile.getFaceUrl();
            DMMsgInfo dmMsgInfo = new DMMsgInfo();
            dmMsgInfo.setText(newMsg);
            dmMsgInfo.setGrade(Integer.parseInt(grade));
            dmMsgInfo.setAvatar(headImg);
            dmMsgInfo.setLiveId(SenderId);
            danmuView.addDanMu(dmMsgInfo);


        } else {
            info = new LiveMsgInfo(Integer.parseInt(grade), nickName, msg, SenderId);
        }
        mLmlv_host_live.addMsg(info);

    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }
}
