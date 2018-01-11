package com.example.coco.liveproject.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by coco on 2018/1/11.
 */

public class OkHttpHelper {
    OkHttpClient client;
    Gson gson;
    Handler mainHandler;
    private static OkHttpHelper helper;

    public OkHttpHelper() {
        client = new OkHttpClient.Builder().build();
        gson = new Gson();
        Looper mainLooper = Looper.getMainLooper();
        mainHandler = new Handler(mainLooper);

    }

    public static OkHttpHelper getInstance() {
        if (helper == null) {
            synchronized (OkHttpHelper.class) {
                if (helper == null) {
                    helper = new OkHttpHelper();
                }
            }
        }
        return helper;
    }

    public void getString(String url, final OnRequestComplete<String> onRequestComplete) {
        if (!url.startsWith("http")) {
            //提示网址不对，访问失败
            if (onRequestComplete != null) {
                onRequestComplete.onFailed();
            }
            return;
        }
        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String string = response.body().string();
                    if (TextUtils.isEmpty(string)) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onSuccess(string);
                            }
                        });
                    }
                } else {
                    //服务器错误
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onError();
                        }
                    });
                }

            }
        });
    }

    public void postString(String url, HashMap<String, String> map, final OnRequestComplete<String> onRequestComplete) {
        if (!url.startsWith("http")) {
            //提示网址不对，访问失败
            if (onRequestComplete != null) {
                onRequestComplete.onFailed();
            }
            return;
        }
        FormBody.Builder fBuilder = new FormBody.Builder();
        FormBody formBody = null;
        if (map != null && !map.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String>entrys:entrySet){
                fBuilder.add(entrys.getKey(),entrys.getValue());

            }
            formBody=fBuilder.build();
        }

        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(url).post(formBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String string = response.body().string();
                    if (TextUtils.isEmpty(string)) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onSuccess(string);
                            }
                        });
                    }
                } else {
                    //服务器错误
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onError();
                        }
                    });
                }

            }
        });
    }
    public void getObject(String url, final OnRequestComplete onRequestComplete, final Class clazz){
        if (!url.startsWith("http")) {
            //提示网址不对，访问失败
            if (onRequestComplete != null) {
                onRequestComplete.onFailed();
            }
            return;
        }
        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)){
                        final Object obj = gson.fromJson(string, clazz);
                        if (obj==null){
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onRequestComplete.onEmpty();
                                }
                            });
                        }else{
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onRequestComplete.onSuccess(obj);
                                }
                            });
                        }
                    }else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    }
                }else {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onError();
                        }
                    });
                }

            }
        });
    }
public void postObject(String url, HashMap<String,String>map, final OnRequestComplete onRequestComplete, final Class clazz){
    if (!url.startsWith("http")) {
        //提示网址不对，访问失败
        if (onRequestComplete != null) {
            onRequestComplete.onFailed();
        }
        return;
    }
    FormBody.Builder fBuilder = new FormBody.Builder();
    FormBody formBody = null;
    if (map != null && !map.isEmpty()) {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String>entrys:entrySet){
            fBuilder.add(entrys.getKey(),entrys.getValue());

        }
        formBody=fBuilder.build();
    }

    Request.Builder builder = new Request.Builder();
    final Request request = builder.url(url).post(formBody).build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    onRequestComplete.onFailed();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()){
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)){
                    final Object obj = gson.fromJson(string, clazz);
                    if (obj==null){
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onEmpty();
                            }
                        });
                    }else{
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete.onSuccess(obj);
                            }
                        });
                    }
                }else {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete.onEmpty();
                        }
                    });
                }
            }else {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestComplete.onError();
                    }
                });
            }

        }
    });
}
    public interface OnRequestComplete<T> {
        void onSuccess(T t);

        void onEmpty();

        void onError();

        void onFailed();

        //需要服务器配合，当加载的数据为最终数据，或者所有数据
        //时，才触发（服务器可以提供字段，或者总数量）
        void onDataComplete();
    }
}
