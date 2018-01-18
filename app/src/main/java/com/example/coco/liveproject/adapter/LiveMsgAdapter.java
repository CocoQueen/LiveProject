package com.example.coco.liveproject.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.LiveMsgInfo;

import java.util.ArrayList;

/**
 * Created by coco on 2018/1/16.
 */

public class LiveMsgAdapter extends BaseAdapter {
    private ArrayList<LiveMsgInfo> list;
    private Context context;

    public LiveMsgAdapter(ArrayList<LiveMsgInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.live_msg_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.setContent(list.get(position));
        }
        return convertView;
    }

    public class ViewHolder {
        TextView mTv_live_msg;

        public ViewHolder(View convertView) {
            mTv_live_msg = convertView.findViewById(R.id.mTv_live_msg_item);
        }

        public void setContent(LiveMsgInfo info) {
            String liveId = info.getLiveId();
            String text = info.getText();
            SpannableStringBuilder builder = new SpannableStringBuilder("");
            SpannableString string = new SpannableString(liveId + ":");
            int startIndex = 0;
            int endIndex = liveId.length()+1;
            string.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)),startIndex,endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            SpannableString string1 = new SpannableString(text);
            int startIndex1=0;
            int endIndex1=text.length();
            string1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)),startIndex1,endIndex1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            builder.append(string);
            builder.append(string1);
            mTv_live_msg.setText(builder);
        }
    }
}
