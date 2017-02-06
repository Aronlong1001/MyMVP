package test.aron.com.fragmentdemo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import test.aron.com.fragmentdemo.R;
import test.aron.com.fragmentdemo.ui.activity.base.BaseActivity;
import test.aron.com.fragmentdemo.ui.adapter.MyPagerAdapter;
import test.aron.com.fragmentdemo.ui.fragment.ItemFragment;
import test.aron.com.fragmentdemo.util.MyUtils;

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private List<String> mTabNames;
    private String mCurrentViewPagerName;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);//设置toolbar，注意theme不能使用带有actionbar的样式，否则会报错
        initViewPager();
    }

    private void initViewPager() {
        List<String> title = initData();
        List<String> type = initType();
        for (int i = 0; i < title.size(); i++) {
            ItemFragment mFragment = new ItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", type.get(i));
            mFragment.setArguments(bundle);
            mFragmentList.add(mFragment);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), title, mFragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        MyUtils.dynamicSetTabLayoutMode(mTabLayout);
        setPageChangeListener();
        mTabNames = title;
        int currentViewPagerPosition = getCurrentViewPagerPosition();
        mViewPager.setCurrentItem(currentViewPagerPosition, false);
    }

    private int getCurrentViewPagerPosition() {
        int position = 0;
        if (mCurrentViewPagerName != null) {
            for (int i = 0; i < mTabNames.size(); i++) {
                if (mCurrentViewPagerName.equals(mTabNames.get(i))) {
                    position = i;
                }
            }
        }
        return position;
    }

    private void setPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentViewPagerName = mTabNames.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        list.add("头条");
        list.add("财经");
        list.add("社会");
        list.add("军事");
        list.add("科技");
        return list;
    }

    private List<String> initType() {
        List<String> typeList = new ArrayList<>();
        typeList.add("headline");
        typeList.add("finance");
        typeList.add("society");
        typeList.add("military");
        typeList.add("tech");
        return typeList;
    }
}
