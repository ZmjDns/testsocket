package com.zmj.mvp.testsocket.fragmentLazyLoad;

import android.support.v4.app.Fragment;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/22
 * Description :Fragment的懒加载模式  https://blog.csdn.net/zhangrgg/article/details/81000238
 * 使用方法：
 * ***** 必须继承 FragmentPagerAdapter/FragmentStatePagerAdapter，才可以对setUserVisibleHint()调用
 * setUserVisibleHint() 设置Fragment的可见状态
 * getUserVisibleHint() 获取Fragment的可见状态
 * mIsVisible 是父类的成员变量，子类不需要重写
 * mIsprepared 子类需要在OnCreateView()中重写
 * mHasLoadedOnce 子类需要在lazyLoad()中重写
 * <p>
 * 接入步骤：
 * 1:继承BaseLazyFragment,实现lazyLoad()
 * 2:在onCreateView()中，添加以下代码：
 * mIsprepared = true;
 * lazyLoad();
 * 3:重写lazyLoad(){
 * if (!mIsprepared || !mIsVisible || mHasLoadedOnce) {
 * return;
 * }
 * mHasLoadedOnce = true;
 * //UI和业务逻辑
 * }
 * 4:拓展方法
 * stopLoad()://当视图已经对用户不可见并且加载过数据，
 * //如果需要在切换到其他页面时停止加载数据，可以覆写此方法
 * //此方法只在Fragment之间切换生效
 * visibleToUser(): 用户可看见当前的Fragment (神策埋点需要)
 * inVisibleToUser():用户看不见当前的Fragment，包含Fragment之间切换和跳转到Activity (神策埋点需要)
 */
public abstract class BaseLazyfragment extends Fragment {
    //判断当前Fragment是否可见
    protected boolean mIsVisible;
    //标志位，标志已经初始化完成
    protected boolean mIsPrepared;
    //标志位，是否已经加载过一次，第二次就不用加载了
    protected boolean mHasLoadOnce;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //设置fragment的可见状态
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){//获取Fragment的可见状态
            mIsVisible = true;//此时可见
            onVisible();
        }else {
            mIsVisible = false;
            onInvisible();
        }

        if (isResumed()){
            onVisibilityChangedToUser(isVisibleToUser);
        }

    }

    //可见
    protected void onInvisible(){
        lazyLoad();
    }

    //不可见
    protected void onVisible(){
        stopLoad();
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见，并且已经加载过数据，如果在切换到其他界面上上停止加载数据可以重写此方法
     */
    protected void stopLoad() {

    }

    //region 神策埋点需要，统计Fragment 可见时间
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            onVisibilityChangedToUser(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()){
            onVisibilityChangedToUser(false);
        }
    }

    /**
     * 当Fragment对用户的可见性发生了变化时回调
     * @param isVisibleToUser  true：用户能看见此fragment     false：用户不能看见此fragment
     */
    public void onVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser){
            visibleToUser();
        }else {
            inVisibleToUser();
        }

    }

    protected void visibleToUser() {
    }

    private void inVisibleToUser() {
    }
}
