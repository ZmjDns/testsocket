package com.zmj.mvp.testsocket.chatview.iviewipersent;

import com.zmj.mvp.testsocket.bean.User;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/30
 */
public interface IKefuView {

    /**
     *
     */
    void showLoading();
    /**
     * 获取到客服数据
     */
    void showKefus(List<User> users);


}
