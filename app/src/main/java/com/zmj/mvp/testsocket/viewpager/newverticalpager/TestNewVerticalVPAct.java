package com.zmj.mvp.testsocket.viewpager.newverticalpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.zmj.mvp.testsocket.R;

import java.util.ArrayList;
import java.util.List;

public class TestNewVerticalVPAct extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private VerticalViewPager vvp_new_pager;

    private List<String> strings = new ArrayList<>();

    private VerticalVPAdapter verticalVPAdapter;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test_new_vertical_vp);

        initViews();
    }

    private void initViews() {
        vvp_new_pager = findViewById(R.id.vvp_new_pager);

        strings.add("AAA");
        strings.add("BBB");
        strings.add("CCC");
        strings.add("DDD");
        strings.add("EEE");


        //FragmentAdapter
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.setData(strings);
        vvp_new_pager.setAdapter(fragmentAdapter);
        //PagerAdapter
//        verticalVPAdapter = new VerticalVPAdapter(getSupportFragmentManager());
//        verticalVPAdapter.setUrlLists(strings);
//        vvp_new_pager.setAdapter(verticalVPAdapter);
        //vvp_new_pager.setAdapter(new InnerFragmentAdapter(getSupportFragmentManager()));

        vvp_new_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == strings.size() - 2){
                    //此时已经到倒数第3个了，开始加载数据
                    addData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void addData(){
        strings.add("FFF");
        strings.add("GGG");
        strings.add("HHH");
        strings.add("III");
        strings.add("JJJ");

        fragmentAdapter.setData(strings);
        fragmentAdapter.notifyDataSetChanged();

//        verticalVPAdapter.setUrlLists(strings);
//        verticalVPAdapter.notifyDataSetChanged();
    }

    class InnerFragmentAdapter extends FragmentPagerAdapter{


        public InnerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem: 当前位置：" + position);
            if (position == 0){
                return new FragmentOne();
            }
            Fragment fragment = new FragmentTwo();
            Bundle bundle = new Bundle();
            bundle.putString("aaa","testINfo");
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1000;
        }
    }
}
