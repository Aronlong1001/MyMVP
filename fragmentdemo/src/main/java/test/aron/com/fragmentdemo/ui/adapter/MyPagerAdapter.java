package test.aron.com.fragmentdemo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aron on 2016/12/28.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<String> mList;
    private List<Fragment> mNewsFragmentList = new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm, List<String> list, List<Fragment> newsFragmentLis) {
        super(fm);
        this.mList = list;
        mNewsFragmentList = newsFragmentLis;
    }

    @Override
    public Fragment getItem(int position) {
        return mNewsFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }
}
