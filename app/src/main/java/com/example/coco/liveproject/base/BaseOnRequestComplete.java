package com.example.coco.liveproject.base;

import com.example.coco.liveproject.utils.OkHttpHelper;
import com.example.coco.liveproject.utils.ToastUtils;

/**
 * Created by coco on 2018/1/11.
 */

public abstract class BaseOnRequestComplete<T> implements OkHttpHelper.OnRequestComplete<T> {


    @Override
    public void onEmpty() {
        ToastUtils.show("数据为空");
    }

    @Override
    public void onError() {
        ToastUtils.show("访问数据失败");

    }

    @Override
    public void onFailed() {
        ToastUtils.show("访问服务器失败");

    }

    @Override
    public void onDataComplete() {
        ToastUtils.show("数据加载完成");


    }
}
