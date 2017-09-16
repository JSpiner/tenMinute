package com.noname.tenminute.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.noname.tenminute.Adapter.TabAdapter;
import com.noname.tenminute.Fragment.ChatFragment;
import com.noname.tenminute.Fragment.EvaluateFragment;
import com.noname.tenminute.Fragment.HomeFragment;
import com.noname.tenminute.Fragment.LogFragment;
import com.noname.tenminute.Fragment.SettingFragment;
import com.noname.tenminute.Model.ProfileModel;
import com.noname.tenminute.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.pager)
    ViewPager mPager;

    TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //TODO : 알아서 리팩토링해라 ㅅㄱ
        ProfileModel profileModel = (ProfileModel) getIntent().getSerializableExtra("profile");
        String userName = getIntent().getStringExtra("username");

        mTabLayout.setSelectedTabIndicatorHeight(0);

        tabAdapter = new TabAdapter(getSupportFragmentManager(), this);
        tabAdapter.addFragment(new HomeFragment().setProfileModel(profileModel).setUserName(userName));
        tabAdapter.addFragment(new LogFragment());
        tabAdapter.addFragment(new EvaluateFragment());
        tabAdapter.addFragment(new ChatFragment());
        tabAdapter.addFragment(new SettingFragment());

        mPager.setAdapter(tabAdapter);
        mTabLayout.setupWithViewPager(mPager);

        for(int i=0; i<mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(tabAdapter.getTabView(i));
        }

        mTabLayout.getTabAt(0).select();
    }
}
