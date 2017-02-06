package test.aron.com.mvpdemo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import test.aron.com.mvpdemo.R;
import test.aron.com.mvpdemo.common.Common;
import test.aron.com.mvpdemo.entity.NewsChannelTable;
import test.aron.com.mvpdemo.ui.activity.base.BaseActivity;
import test.aron.com.mvpdemo.ui.adapter.NewsFragmentPagerAdapter;
import test.aron.com.mvpdemo.ui.fragment.NewsListFragment;
import test.aron.com.mvpdemo.utils.MyUtils;

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private List<Fragment> mNewsFragmentList = new ArrayList<>();
    private String mCurrentViewPagerName;
    private List<String> mChannelNames;
    private String[] title = {"头条", "财经", "社会", "科技", "军事"};

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initViewPager();
    }

    private void initViewPager() {
        final List<String> channelNames = new ArrayList<>();
        setNewsList(channelNames);
        setViewPager(channelNames);
    }

    private void setNewsList(List<String> channelNames) {
        List<NewsChannelTable> newsChannelTables = initNews();
        for (int i = 0; i < title.length; i++) {
            NewsListFragment newsListFragment = createListFragments(newsChannelTables.get(i));
            mNewsFragmentList.add(newsListFragment);
            channelNames.add(title[i]);
        }
    }

    private NewsListFragment createListFragments(NewsChannelTable newsChannelTable) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("newsId", newsChannelTable.getNewsChannelId());
        bundle.putString("newsType", newsChannelTable.getNewsChannelType());
        fragment.setArguments(bundle);
        return fragment;
    }

    private void setViewPager(List<String> channelNames) {
        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getSupportFragmentManager(), channelNames, mNewsFragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        MyUtils.dynamicSetTabLayoutMode(mTabLayout);
        setPageChangeListener();
        mChannelNames = channelNames;
        int currentViewPagerPosition = getCurrentViewPagerPosition();
        mViewPager.setCurrentItem(currentViewPagerPosition, false);
    }

    private int getCurrentViewPagerPosition() {
        int position = 0;
        if (mCurrentViewPagerName != null) {
            for (int i = 0; i < mChannelNames.size(); i++) {
                if (mCurrentViewPagerName.equals(mChannelNames.get(i))) {
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
                mCurrentViewPagerName = mChannelNames.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onInitData2Remote() {
        super.onInitData2Remote();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private List<NewsChannelTable> initNews() {
        List<NewsChannelTable> list = new ArrayList<>();
        NewsChannelTable bean1 = new NewsChannelTable();
        bean1.setNewsChannelId(Common.HEADLINE_ID);
        bean1.setNewsChannelType(Common.HEADLINE_TYPE);
        list.add(bean1);
        NewsChannelTable bean2 = new NewsChannelTable();
        bean2.setNewsChannelId(Common.FINANCE_ID);
        bean2.setNewsChannelType(Common.OTHER_TYPE);
        list.add(bean2);
        NewsChannelTable bean3 = new NewsChannelTable();
        bean3.setNewsChannelId(Common.SOCIETY_ID);
        bean3.setNewsChannelType(Common.OTHER_TYPE);
        list.add(bean3);
        NewsChannelTable bean4 = new NewsChannelTable();
        bean4.setNewsChannelId(Common.TECH_ID);
        bean4.setNewsChannelType(Common.OTHER_TYPE);
        list.add(bean4);
        NewsChannelTable bean5 = new NewsChannelTable();
        bean5.setNewsChannelId(Common.MILITARY_ID);
        bean5.setNewsChannelType(Common.OTHER_TYPE);
        list.add(bean5);
        return list;
    }
}
