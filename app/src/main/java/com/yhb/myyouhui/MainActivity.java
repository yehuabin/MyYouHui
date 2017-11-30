package com.yhb.myyouhui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobstat.StatService;
import com.pgyersdk.update.PgyUpdateManager;
import com.yhb.myyouhui.fragment.ProductListFragment;
import com.yhb.myyouhui.search.SearchActivity;
import com.yhb.myyouhui.utils.CategoryUtil;
import com.yhb.myyouhui.utils.TaoBaoHelper;
import com.yhb.myyouhui.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {

    TabLayout tab_category;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    NoScrollViewPager vp_list;

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bmob.initialize(this, "9be40913fa1a1f940dc81aafa1139757");
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(MainActivity.this);
        StatService.start(this);
        TaoBaoHelper.loadCookie(null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        tab_category = mViewHolder.get(R.id.tab_category);
        vp_list = mViewHolder.get(R.id.vp_list);
        vp_list.setNoScroll(true);
        final int count = CategoryUtil.getCount();
        for (int i = 0; i < count; i++) {
            fragmentList.add(new ProductListFragment());
        }

        mViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SearchActivity.class);
            }
        }, R.id.ll_search);

        vp_list.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = fragmentList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("type", "index");
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return CategoryUtil.getText(String.valueOf(position));
            }

            @Override
            public int getCount() {
                return count;
            }
            @Override
            public Fragment instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container,
                        position);
                getSupportFragmentManager().beginTransaction().show(fragment).commit();

                return fragment;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // super.destroyItem(container, position, object);
                Fragment fragment = fragmentList.get(position);
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();

            }
        });

        for (int i = 0; i < CategoryUtil.getCount(); i++) {
            tab_category.addTab(tab_category.newTab().setText(CategoryUtil.getText(String.valueOf(i))));
        }
        tab_category.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_category.setupWithViewPager(vp_list);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
