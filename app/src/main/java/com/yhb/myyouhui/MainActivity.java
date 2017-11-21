package com.yhb.myyouhui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.yhb.myyouhui.fragment.ProductListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager vp_list;
    List<ProductListFragment> fragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);


        tabLayout= (TabLayout) findViewById(R.id.tab_category);
        vp_list= (ViewPager) findViewById(R.id.vp_list);


       for (int i=0;i<6;i++){
           fragmentList.add(new ProductListFragment());
       }


        vp_list.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
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
