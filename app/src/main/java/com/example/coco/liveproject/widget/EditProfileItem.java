package com.example.coco.liveproject.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coco.liveproject.R;

/**
 * Created by coco on 2018/1/3.
 */

public class EditProfileItem extends FrameLayout {
    private ItemType type = ItemType.TYPE_NOMAL;

    private LayoutInflater inflater;
    private RelativeLayout mRl;
    private TextView mTv_editprofile_name;
    private TextView mTv_editprofile_value;
    private TextView mTv_seprater;
    private ImageView mImg_editprofile_avatar;
    private ImageView mImg_right_arrow;


    public EditProfileItem(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(getContext());
        initView();
    }

    public EditProfileItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(getContext());
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditProfileItem);
        int itemtype = typedArray.getInt(R.styleable.EditProfileItem_itemtype, 101);
        String itemtitle = typedArray.getString(R.styleable.EditProfileItem_itemtilte);

        if (!TextUtils.isEmpty(itemtitle)) {
            mTv_editprofile_name.setText(itemtitle);
        }
        if (itemtype == ItemType.TYPE_NOMAL.getValue()) {
            setType(ItemType.TYPE_NOMAL);
            boolean isendline = typedArray.getBoolean(R.styleable.EditProfileItem_isendlinfe, false);
            String itemvalue = typedArray.getString(R.styleable.EditProfileItem_itemvalue);
            if (!TextUtils.isEmpty(itemvalue)) {
                mTv_editprofile_value.setText(itemvalue);
            }
            if (isendline) {
                mTv_seprater.setVisibility(GONE);
            }
        } else if (itemtype == ItemType.TYPE_AVATAR.getValue()) {
            setType(ItemType.TYPE_AVATAR);
        }
    }

    private void initView() {
        View view = inflater.inflate(R.layout.eidt_profile_item, null, false);
        mRl = view.findViewById(R.id.mRl);
        int height = getResources().getDimensionPixelOffset(R.dimen.item_height);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mRl.setLayoutParams(layoutParams);

        mTv_editprofile_name = view.findViewById(R.id.mTv_editprofile_name);
        mTv_editprofile_value = view.findViewById(R.id.mTv_editprofile_value);
        mTv_seprater = view.findViewById(R.id.mTv_seprater);
        mImg_editprofile_avatar = view.findViewById(R.id.mImg_editprofile_avatar);
        mImg_right_arrow = view.findViewById(R.id.mImg_right_arrow);

        addView(view);

    }

    public void setType(ItemType type) {
        this.type = type;
        if (type == ItemType.TYPE_AVATAR) {
            mTv_editprofile_value.setVisibility(INVISIBLE);
            mImg_right_arrow.setVisibility(INVISIBLE);
            mImg_editprofile_avatar.setVisibility(VISIBLE);
        } else if (type == ItemType.TYPE_NOMAL) {
            mTv_editprofile_value.setVisibility(VISIBLE);
            mImg_right_arrow.setVisibility(VISIBLE);
            mImg_editprofile_avatar.setVisibility(INVISIBLE);
        }
    }

    public void setText(String name, String value) {
        mTv_editprofile_name.setText(name);
        mTv_editprofile_value.setText(value);

    }

    public enum ItemType {
        TYPE_AVATAR(102), TYPE_NOMAL(101);
        private int value;

        ItemType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
