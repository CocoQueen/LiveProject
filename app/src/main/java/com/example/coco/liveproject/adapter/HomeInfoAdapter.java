package com.example.coco.liveproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.HomeInfo;
import com.example.coco.liveproject.utils.ImageUtils;
import com.example.coco.liveproject.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by coco on 2018/1/14.
 */

public class HomeInfoAdapter extends RecyclerView.Adapter<HomeInfoAdapter.HomeInfoViewHolder> {
    Context context;
    ArrayList<HomeInfo.DataBean> list;
    OnHomeInfoListener listener;
    private View view;


    public HomeInfoAdapter(Context context, ArrayList<HomeInfo.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public HomeInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.home_info_adapter, null, false);
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.item_height2);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, offset);
        view.setLayoutParams(layoutParams);
        return new HomeInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeInfoViewHolder holder, int position) {
        final HomeInfo.DataBean bean = list.get(position);
        if (bean != null) {
            holder.setContent(bean);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onHomeInfoListener(bean);
                    ToastUtils.show("进入" + bean.getUserName() + "的房间");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeInfoViewHolder extends RecyclerView.ViewHolder {
        TextView mTv_title_adapter, mTv_live_id_adapter, mTv_watcher_num_adapter;
        ImageView mImg_bg_adapter;

        public HomeInfoViewHolder(View itemView) {
            super(itemView);
            mImg_bg_adapter = itemView.findViewById(R.id.mImg_bg_adapter);
            mTv_live_id_adapter = itemView.findViewById(R.id.mTv_live_id_adapter);
            mTv_title_adapter = itemView.findViewById(R.id.mTv_title_adapter);
            mTv_watcher_num_adapter = itemView.findViewById(R.id.mTv_watcher_num_adapter);

        }

        public void setContent(HomeInfo.DataBean bean) {
            mTv_title_adapter.setText(bean.getLiveTitle());
            mTv_live_id_adapter.setText(bean.getUserId());
            mTv_watcher_num_adapter.setText(bean.getWatcherNums() + "");
            mImg_bg_adapter.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageUtils.getInstance().loadPic(bean.getLiveCover(), mImg_bg_adapter);
        }
    }

    public interface OnHomeInfoListener {
        void onHomeInfoListener(HomeInfo.DataBean bean);
    }

    public void setOnHomeInfoListener(OnHomeInfoListener listener) {
        this.listener = listener;
    }
}
