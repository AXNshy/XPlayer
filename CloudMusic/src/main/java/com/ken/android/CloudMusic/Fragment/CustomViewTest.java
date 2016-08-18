package com.ken.android.CloudMusic.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.ken.android.CloudMusic.Activity.BaseActivity;
import com.ken.android.CloudMusic.Adapter.MyFragmentPagerAdapter;
import com.ken.android.CloudMusic.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by axnshy on 16/8/18.
 */
@ContentView(R.layout.custom_view_activity)
public class CustomViewTest extends BaseActivity {

    private List<Fragment> fragments;
    private MyFragmentPagerAdapter pagerAdapter;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.tabs)
    private TabLayout tabLayout;
    @ViewInject(R.id.toolbar_top)
    private Toolbar toolbar;
    @ViewInject(R.id.iv_back)
    private ImageView returnImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        CustomViewOne viewone = new CustomViewOne();
        CustomViewTwo viewtwo = new CustomViewTwo();
        CustomViewThree viewthree = new CustomViewThree();
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);
        returnImg= (ImageView) findViewById(R.id.iv_back);
        returnImg.setImageResource(R.drawable.ic_chevron_left_white_48);
        fragments = new ArrayList<>();
        fragments.add(viewone);
        fragments.add(viewtwo);
        fragments.add(viewthree);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
//        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }
}
