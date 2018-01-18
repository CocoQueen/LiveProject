package com.example.coco.liveproject.custom;

import com.tencent.TIMFriendshipManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by coco on 2018/1/3.
 */

public interface ProfileInfoCustom {
    String PREFIX="Tag_Profile_Custom_";
    String INFO_SEND=PREFIX+"send";
    String INFO_RECEIVE=PREFIX+"receive";
    String INFO_GRADE=PREFIX+"grade";
    String INFO_XINGZUO=PREFIX+"xingzuo";
    String INFO_FANS=PREFIX+"fans";
    String INFO_FORK=PREFIX+"fork";
    String []name ={INFO_SEND,INFO_RECEIVE,INFO_GRADE,INFO_XINGZUO,INFO_FANS,INFO_FORK};
    List<String>names= Arrays.asList(name);

    //腾讯自带的信息
    long ALL_BASE_INFO =
            TIMFriendshipManager.TIM_PROFILE_FLAG_BIRTHDAY
                    |TIMFriendshipManager.TIM_PROFILE_FLAG_FACE_URL
                    |TIMFriendshipManager.TIM_PROFILE_FLAG_GENDER
                    |TIMFriendshipManager.TIM_PROFILE_FLAG_LANGUAGE
                    |TIMFriendshipManager.TIM_PROFILE_FLAG_LOCATION
                    |TIMFriendshipManager.TIM_PROFILE_FLAG_NICK
                    |TIMFriendshipManager.TIM_PROFILE_FLAG_SELF_SIGNATURE
                    |TIMFriendshipManager.TIM_PROFILE_FLAG_REMARK
                    |TIMFriendshipManager.TIM_PROFILE_FLAG_GROUP;


    String TYPE_MSG = "MSG+****";
    String TYPE_DAN = "DAN+****";
    int TEXT_MSG = 0x801;
    int DANMU_MSG=0x802;
}
