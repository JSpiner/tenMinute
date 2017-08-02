package com.noname.tenminute.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.noname.tenminute.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by PJC on 2017-07-30.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragmentList;
    private Context mContext;

    public static final int TAB_HOME = 0;
    public static final int TAB_EVALUATE = 1;
    public static final int TAB_LOG = 2;
    public static final int TAB_CHAT = 3;
    public static final int TAB_SETTING = 4;

    private int[] mTabIcon = {
            R.drawable.tab1_selector,
            R.drawable.tab3_selector,
            R.drawable.tab2_selector,
            R.drawable.tab4_selector,
            R.drawable.tab5_selector
    };

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentList = new ArrayList<>();
        mContext = context;
    }

    public View getTabView(int position) {
        View v= null;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_tab, null);
        v.findViewById(R.id.icon).setBackgroundResource(mTabIcon[position]);
        //imageView.setImageResource(mTabIcon[position]);
        return v;
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public Fragment getFragment(int fNum) {
        return mFragmentList.get(fNum);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
