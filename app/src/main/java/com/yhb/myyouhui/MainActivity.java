package com.yhb.myyouhui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.yhb.myyouhui.fragment.ProductListFragment;
import com.yhb.myyouhui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    TabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    ViewPager vp_list;
    List<ProductListFragment> fragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);


        tabLayout= mViewHolder.get(R.id.tab_category);
        vp_list= mViewHolder.get(R.id.vp_list);


       for (int i=0;i<6;i++){
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
                Fragment fragment=fragmentList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("keyword", "女装");
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return 5;
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("女装"));
        tabLayout.addTab(tabLayout.newTab().setText("男装"));
        tabLayout.addTab(tabLayout.newTab().setText("男装"));
        tabLayout.addTab(tabLayout.newTab().setText("男装"));
        tabLayout.addTab(tabLayout.newTab().setText("男装"));
        tabLayout.addTab(tabLayout.newTab().setText("男装"));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
