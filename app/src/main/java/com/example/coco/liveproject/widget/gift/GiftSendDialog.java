package com.example.coco.liveproject.widget.gift;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.GiftInfo;

import java.util.ArrayList;

/**
 * Created by coco on 2018/1/18.
 */

public class GiftSendDialog {
    Activity activity;
    private final Dialog dialog;
    ArrayList<GiftGridView> gridViewList = new ArrayList<>();
    ArrayList<GiftInfo> allList = new ArrayList<>();
    private WindowManager manager;
    private Display display;
    private LayoutInflater inflater;
    private View view;
    private ViewPager mVp_gift;
    private ImageView mImg_indicator0;
    private ImageView mImg_indicator1;
    private Button mBtn_send_gift;
    private GiftGridView.SetGiftDefaultListener defaultListener;
    private GiftInfo giftInfo;
    private GiftGridView gridView;
    private GiftGridView gridView2;
    private ArrayList<GiftInfo> gifts1;
    private ArrayList<GiftInfo> gifts2;
    private GiftViewPagerAdapter adapter;
    private onGiftSendListener listener;

    public GiftSendDialog(Activity activity,onGiftSendListener listener) {
        this.activity = activity;
        dialog = new Dialog(activity);
        this.listener=listener;
        initView();
        initListener();
    }

    private void initListener() {

        mVp_gift.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
                    mImg_indicator0.setBackgroundResource(R.mipmap.indicator_selected);
                    mImg_indicator1.setBackgroundResource(R.mipmap.indicator_normal);
                } else if (1 == position) {
                    mImg_indicator0.setBackgroundResource(R.mipmap.indicator_normal);
                    mImg_indicator1.setBackgroundResource(R.mipmap.indicator_selected);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public GiftSendDialog(Activity activity, int themeResId,onGiftSendListener listener) {
        this.activity = activity;
        dialog = new Dialog(activity, themeResId);
        this.listener=listener;
        initView();
        initListener();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void show() {
        dialog.show();
    }

    private void initView() {
        initAllGift();
        manager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
        inflater = LayoutInflater.from(activity);
        view = inflater.inflate(R.layout.dialog_send_gift, null, false);
        mVp_gift = view.findViewById(R.id.mVp_gift);
        mImg_indicator0 = view.findViewById(R.id.mImg_indicator0);
        mImg_indicator1 = view.findViewById(R.id.mImg_indicator1);
        mBtn_send_gift = view.findViewById(R.id.mBtn_send_gift);

        mBtn_send_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onSend(giftInfo);
                }
            }
        });
        initGridView();

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = display.getWidth();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }

    private void initGridView() {
        defaultListener = new GiftGridView.SetGiftDefaultListener() {
            @Override
            public void setOnSelected(GiftInfo info) {
                giftInfo = info;
                gridView.setGiftSelected(info);
                gridView2.setGiftSelected(info);

            }

            @Override
            public void setOnUnSelected(GiftInfo info) {
                    giftInfo=null;
            }
        };
        gifts1 = new ArrayList<>();
        gifts2 = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 8;
        gifts1.addAll(allList.subList(startIndex, endIndex));
        gifts2.addAll(allList.subList(endIndex, allList.size()));

        gridView = new GiftGridView(activity,defaultListener);
        gridView.setGiftData(gifts1);
        gridView2 = new GiftGridView(activity,defaultListener);
        gridView2.setGiftData(gifts2);

        gridViewList.add(gridView);
        gridViewList.add(gridView2);

        adapter = new GiftViewPagerAdapter();
        mVp_gift.setAdapter(adapter);

    }

    private void initAllGift() {
        allList.add(GiftInfo.gift);
        allList.add(GiftInfo.gift1);
        allList.add(GiftInfo.gift2);
        allList.add(GiftInfo.gift3);
        allList.add(GiftInfo.gift4);
        allList.add(GiftInfo.gift5);
        allList.add(GiftInfo.gift6);
        allList.add(GiftInfo.gift7);
        allList.add(GiftInfo.gift8);
        allList.add(GiftInfo.gift9);
        allList.add(GiftInfo.gift10);
        allList.add(GiftInfo.gift11);
        allList.add(GiftInfo.giftEmpty);
        allList.add(GiftInfo.giftEmpty);
        allList.add(GiftInfo.giftEmpty);
        allList.add(GiftInfo.giftEmpty);

    }
    class GiftViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return gridViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            GiftGridView gridView = gridViewList.get(position);
            if (gridView.getParent() == null) {
                container.addView(gridView);
            }
            return gridView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
            container.removeView(gridViewList.get(position));
        }
    }
    public interface onGiftSendListener{
        void onSend(GiftInfo info);
    }
    public void setSendButtonText(String str){
        mBtn_send_gift.setText(str);

    }
}
