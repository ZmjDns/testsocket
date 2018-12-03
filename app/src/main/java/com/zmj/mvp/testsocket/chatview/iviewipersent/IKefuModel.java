package com.zmj.mvp.testsocket.chatview.iviewipersent;

import com.zmj.mvp.testsocket.bean.User;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/30
 */
public interface IKefuModel {

    /**
     * 加载方法
     * @param listener
     */
    void loadKefu(OnLoadKefuListener listener);

    /**
     * 加载成功监听
     */
    interface OnLoadKefuListener{
        void onComplete(List<User> kefuList);
    }

}
