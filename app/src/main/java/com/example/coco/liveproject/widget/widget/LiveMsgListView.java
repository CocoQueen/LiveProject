package com.example.coco.liveproject.widget.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.coco.liveproject.adapter.LiveMsgAdapter;
import com.example.coco.liveproject.bean.LiveMsgInfo;

import java.util.ArrayList;

/**
 * Created by coco on 2018/1/16.
 */

public class LiveMsgListView extends ListView {
    ArrayList<LiveMsgInfo> list;
    private LiveMsgAdapter adapter;

    public LiveMsgListView(Context context) {
        super(context);
        init();
    }

    public LiveMsgListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setDividerHeight(0);
    }

    public void setData(ArrayList<LiveMsgInfo> list) {
        this.list = list;
        adapter = new LiveMsgAdapter(list, getContext());
        setAdapter(adapter);
    }

    public void addMsg(LiveMsgInfo info) {
        list.add(info);
        adapter.notifyDataSetChanged();
    }

}
