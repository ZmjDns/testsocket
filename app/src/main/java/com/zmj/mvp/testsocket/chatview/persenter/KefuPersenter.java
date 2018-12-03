package com.zmj.mvp.testsocket.chatview.persenter;

import com.zmj.mvp.testsocket.bean.User;
import com.zmj.mvp.testsocket.chatview.iviewipersent.IKefuModel;
import com.zmj.mvp.testsocket.chatview.iviewipersent.IKefuView;
import com.zmj.mvp.testsocket.chatview.model.KefuInfoModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/30
 */
public class KefuPersenter<T extends IKefuView> {

    //1.持有view的引用
    //private IKefuView iKefuView;
    private WeakReference<T> mWeakRef;

    //2.有model的引用
    private KefuInfoModel kefuInfoModel = new KefuInfoModel();

    public KefuPersenter() {

    }

    public void attAchView(T view){
        this.mWeakRef = new WeakReference<>(view);
    }

    public void dechView(){
        mWeakRef.clear();
    }

    public void featchDat(){
        if (mWeakRef.get() != null){
            mWeakRef.get().showLoading();
            if (kefuInfoModel != null){
                kefuInfoModel.loadKefu(new IKefuModel.OnLoadKefuListener() {
                    @Override
                    public void onComplete(List<User> kefuList) {
                        if (mWeakRef.get() != null){
                            mWeakRef.get().showKefus(kefuList);
                        }
                    }
                });
            }
        }
    }
}
