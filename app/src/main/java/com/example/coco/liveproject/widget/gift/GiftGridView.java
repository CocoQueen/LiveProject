package com.example.coco.liveproject.widget.gift;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.GiftInfo;

import java.util.ArrayList;

/**
 * Created by coco on 2018/1/18.
 */

public class GiftGridView extends GridView {
    ArrayList<GiftInfo> list = new ArrayList<>();
    private LayoutInflater inflater;
    private GiftAdapter adapter;
    SetGiftDefaultListener listener;

    public GiftGridView(Context context, SetGiftDefaultListener listener) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.listener=listener;
        init();
    }

    public GiftGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        setNumColumns(4);

        adapter = new GiftAdapter();
        setAdapter(adapter);

    }

    public void setGiftData(ArrayList<GiftInfo> infoList) {
        list.clear();
        list = infoList;
        adapter.notifyDataSetChanged();
    }

    public interface SetGiftDefaultListener {
        void setOnSelected(GiftInfo info);
        void setOnUnSelected(GiftInfo info);
    }

    public void setGiftSelected(GiftInfo info) {
        for (GiftInfo giftInfo : list) {
            if (giftInfo.getGiftId() == info.getGiftId()) {
                giftInfo.setSelected(true);
            } else {
                giftInfo.setSelected(false);
            }
            adapter.notifyDataSetChanged();
        }
    }
    class GiftAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
          ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gift_item_adapter, null, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            final GiftInfo gInfo = list.get(position);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gInfo.isSelected()){
                        gInfo.setSelected(false);
                    }else {
                    listener.setOnSelected(gInfo);
                    }
                }
            });

            holder.bindData(gInfo);
            return convertView;
        }
        class ViewHolder {

            private final ImageView mImg_gift_icon;
            private final ImageView mImg_gift_select;
            private final TextView mTv_gift_name;
            private final TextView mTv_gift_price;

            public ViewHolder(View v) {
                mImg_gift_icon = v.findViewById(R.id.mImg_gift_icon);
                mImg_gift_select = v.findViewById(R.id.mImg_gift_select);
                mTv_gift_name = v.findViewById(R.id.mTv_gift_name);
                mTv_gift_price = v.findViewById(R.id.mTv_gift_price);
            }

            public void bindData(GiftInfo info) {
                if (info != null) {
                    if (info.getResId() != 0) {
                        mImg_gift_icon.setBackgroundResource(info.getResId());
                    } else {
                        mImg_gift_icon.setBackgroundColor(getContext().getResources().getColor(R.color.transprant));
                    }
                    if (!TextUtils.isEmpty(info.getGiftName())) {
                        mTv_gift_name.setText(info.getGiftName());
                    }
                    mTv_gift_price.setText(info.getPrice() + "星币");
                    if (info.isSelected()) {
                        mImg_gift_select.setBackgroundResource(R.mipmap.right);
                    } else {
                        mImg_gift_select.setBackgroundColor(getContext().getResources().getColor(R.color.transprant));
                    }
                }
            }

        }
    }

}
