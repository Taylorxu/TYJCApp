package com.wise.www.tyjcapp.main.ortherPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Administrator on 2018/3/21.
 */

public class WarningPageAdapter extends FragmentPagerAdapter {

    Fragment fragments[] = new Fragment[]{new WarningListFragment(), new WarningHistoryListFragment()};
    String title[];

    public WarningPageAdapter(FragmentManager fm, String pageTitle[]) {
        super(fm);
        title = pageTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
