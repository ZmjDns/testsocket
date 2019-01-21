package com.zmj.mvp.testsocket.rxjava2;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.retrofit.GetRequest_Interface;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * @author Zmj
 * @date 2019/1/21
 */
public class TestRxJavaAct1 extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ImageView iv_show_pic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_rxjava_1);

        iv_show_pic = findViewById(R.id.iv_show_pic);

        //tst1();
        //simpleTest1();
        //changeThread();
        //loadPic();
        //useMap();
        //useZip();
        //useFrom();
        //useFilter();
        //useTake();
        //useDoNext();
        sinenceOfAndroid();
    }

    /**
     * 1.实例Observable
     * 2.实例Observer
     * 3.订阅observable.subscribe(observer)
     * 4.进入observer的subscribe（）方法
     * 5.进入Observable的subscribe（）方法
     * 6.observable内的onNext（）
     * 7.observer内的onNext（）
     * 8.observable内的onComplete（）
     * 9.observable内的onComplete（）
     *依次交替执行
     */
    private void tst1(){
        //创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello");
                emitter.onNext("hello2");
                emitter.onComplete();       //断开连接，observer将接收不到observable发送的数据
                emitter.onNext("hello Error");
            }
        });

        //创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: onSubscribe" + d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };

        Log.d(TAG, "tst1: subscribe");
        observable.subscribe(observer);
    }

    /**
     * 简化tst1
     */
    private void simpleTest1(){
        Observable<String> observable = Observable.just("hello2");

        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "accept: " + o);
            }
        };

        observable.subscribe(consumer);
    }

    /**
     * {@link io.reactivex.functions.Action}替代onComplete（）
     * {@link Consumer}替代onNext（）和onError()
     */
    private void replace(){
        Observable<String> observable = Observable.just("hello3");
        Action onCompleteAction = new Action() {
            @Override
            public void run() throws Exception {
                Log.d(TAG, "run: complete");
            }
        };
        Consumer<String> onNextConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        };
        Consumer<Throwable> onErrorConsumer = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "accept: " + throwable.getMessage());
            }
        };

        observable.subscribe(onNextConsumer,onErrorConsumer,onCompleteAction);
    }

    /**
     * 线程调度
     * 子线程发送数据，主线程接收数据
     */
    private void changeThread(){
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "subscribe: Observable thread is:" + Thread.currentThread().getName());
                Log.d(TAG, "subscribe: emitter 1");
                emitter.onNext(1);
             }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: Observer thread is:" + Thread.currentThread().getName());
                Log.d(TAG, "accept: onNext:" + integer);
            }
        };

//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(consumer);
        observable.subscribeOn(Schedulers.newThread())      //多次调用subscribOn（），只有第一次有效    Schedulers.newThread()开启线程操作
                .subscribeOn(Schedulers.io())               //第二次将不会调用
                .observeOn(AndroidSchedulers.mainThread())  //多次调用observeON（），都会起作用
                .observeOn(Schedulers.io())                 //第二次也会起作用
                .subscribe(consumer);

    }

    /**
     * 耗时线程加载一个图片
     */
    private void loadPic(){
        Observable.just(getDrawableFromUrl("http://baidu.com/icon.png"))
                .subscribeOn(Schedulers.newThread())            //Schedulers.newThread()开启线程操作
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) throws Exception {
                        iv_show_pic.setImageDrawable(drawable);
                    }
                });
    }

    /**
     *Map 将获取的数据转化为自己需要的类型
     * 如 drawable -> bitmap
     */
    private void useMap(){
        Observable.just(getDrawableFromUrl("aaa"))
                .map(new Function<Drawable, Bitmap>() {
                    @Override
                    public Bitmap apply(Drawable drawable) throws Exception {
                        BitmapDrawable bt = (BitmapDrawable) drawable;
                        return bt.getBitmap();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        iv_show_pic.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * zip 通过一个函数将多个Observable发送的事件结合在一起，然后按照  严格的顺序  发送这些组合到一起的事件
     *  它只发射与发射数据项最少的那个Observable一样多的数据。
     */
    private void useZip(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emitter 1");
                emitter.onNext(1);
                Log.d(TAG, "emitter 2");
                emitter.onNext(2);
                Log.d(TAG, "emitter 3");
                emitter.onNext(3);
                Log.d(TAG, "emitter 4");
                emitter.onNext(4);
                Log.d(TAG, "emitter onComplete");
                emitter.onComplete();
            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emitter ");
                emitter.onNext("A");
                Log.d(TAG, "emitter B");
                emitter.onNext("B");
                Log.d(TAG, "emitter C");
                emitter.onNext("C");
                Log.d(TAG, "emitter onComplete2");
                emitter.onComplete();
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext:" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    /**
     * from 在RxAndeoid中的From已经被拆分成3个 fromArray、fromTable和fromFuture接收一个集合作为输入，然后每次输出一个元素给subscriber
     *注意：如果from（）里执行了耗时操作，即使使用了subscribOn(Schedulers.io()),仍然是在主线程中执行，可能会造成页面卡顿，甚至程序崩溃，
     * 所以，耗时操作最好还是放在Observable.create（）中
     */
    private void useFrom(){
        Observable.fromArray(new Integer[]{1,2,3,4,5})
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });

    }

    private void useFilter(){
        Observable.fromArray(new Integer[]{1,2,3,4,5})
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        //偶数返回 true  则表示保留偶数，返回奇数

                        return integer%2 == 0;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: filter   " + integer);
            }
        });
    }

    /**
     * take  最多保留的事件数
     */
    private void useTake(){
        Observable.just("1","2","3","4","5").take(2).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext:  Take     " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * doNext  如果你想在处理下一个事件之前做某些事，就可以调用该方法
     */
    private void useDoNext(){
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        //偶数返回 true  则表示保留偶数，返回奇数
                        return integer%2 == 0;
                    }
                })
                .take(3)  //最多保留3个，也就是说最多保留3个偶数
                .doOnNext(new Consumer<Integer>() {//在消费之前  输出 integer 的hashcode
                    @Override
                    public void accept(Integer integer) throws Exception {
                        //在输出偶数之前，输出他的hashCode
                        Log.d(TAG, "accept:  hashCode = " + integer.hashCode());
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: consumer" + integer);
                    }
                });
    }


    private Drawable getDrawableFromUrl(String url){
        try{
            //Thread.sleep(6000);
        }catch (Exception e){
            e.printStackTrace();
        }

        return getResources().getDrawable(R.drawable.toright);
    }


    private void expendInAndroid(){

    }

    private void sinenceOfAndroid(){
        //1.界面需要等到多个接口并发取完数据，在更新
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("哈哈哈哈");
            }
        }).subscribeOn(Schedulers.newThread());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("heiheihei");
            }
        }).subscribeOn(Schedulers.newThread());


        Observable.merge(observable1,observable2)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: huoqushuju " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        //2.界面按钮需要防止连续点击的情况
        /*RxView.clicks(button)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(TAG, "do clicked!");
                    }
                });*/

        //3.响应式界面，比如勾选了某个checkBox，自动更新对应preference
        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

        Preference<String> username = rxPreferences.getString("username");
        Preference<Boolean> showWhatsNew = rxPreferences.getBoolean("show-whats-new", true);

        username.asObservable().subscribe(new Action1<String>() {
                                              @Override public void call(String username) {
                                                  Log.d(TAG, "Username: " + username);  //读取到当前值
                                              }
                                          }

        RxCompoundButton.checks(showWhatsNewView)
                        .subscribe(showWhatsNew.asAction());*/
    }










}
