package com.yhb.myyouhui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yhb.myyouhui.fragment.ProductListFragment;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    TabLayout tabLayout;

    TabLayout tl_sortType;
    CheckBox ck_onlyQuan;
    CheckBox ck_onlyTmall;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    ViewPager vp_list;
    SearchModel searchModel = new SearchModel();
    List<ProductListFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);


        tabLayout = mViewHolder.get(R.id.tab_category);
        vp_list = mViewHolder.get(R.id.vp_list);

        final int count = 11;
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
                bundle.putString("keyword", "女装");
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return count;
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("女装"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=nzjh&toPage=1
        tabLayout.addTab(tabLayout.newTab().setText("男装"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=ifs&toPage=1&catIds=30&level=1
        tabLayout.addTab(tabLayout.newTab().setText("母婴"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=muying
        tabLayout.addTab(tabLayout.newTab().setText("食品"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=hch
        tabLayout.addTab(tabLayout.newTab().setText("家居"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=jyj
        tabLayout.addTab(tabLayout.newTab().setText("亲宝贝"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=qbb
        tabLayout.addTab(tabLayout.newTab().setText("运动户外"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=kdc
        tabLayout.addTab(tabLayout.newTab().setText("时尚"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=ifs
        tabLayout.addTab(tabLayout.newTab().setText("9块9"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=9k9
        tabLayout.addTab(tabLayout.newTab().setText("20元封顶"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=20k
        tabLayout.addTab(tabLayout.newTab().setText("特价好货"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=tehui
        tabLayout.addTab(tabLayout.newTab().setText("淘宝DIY"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=diy

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TextView tv_onlyQuan = mViewHolder.get(R.id.tv_onlyQuan);
        Drawable img = getResources().getDrawable(R.drawable.quan);
        img.setBounds(0, 0, 50, 50);
        tv_onlyQuan.setCompoundDrawables(img, null, null, null);

        TextView tv_onlyTmall = mViewHolder.get(R.id.tv_onlyTmall);
        img = getResources().getDrawable(R.drawable.tmall);
        img.setBounds(0, 0, 40, 40);
        tv_onlyTmall.setCompoundDrawables(img, null, null, null);

        tl_sortType = mViewHolder.get(R.id.tl_sortType);
        ck_onlyQuan = mViewHolder.get(R.id.ck_onlyQuan);
        ck_onlyTmall = mViewHolder.get(R.id.ck_onlyTmall);
        ck_onlyQuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                search();
            }
        });

    }

    private void search() {
        searchModel.setSortType(tl_sortType.getSelectedTabPosition());
        searchModel.setOnlyQuan(ck_onlyQuan.isChecked());
        searchModel.setOnlyTmall(ck_onlyTmall.isChecked());

        toastLong(String.format("排序类型:%d,优惠券：%s,天猫：%s", searchModel.getSortType(),
                searchModel.isOnlyQuan(), searchModel.isOnlyTmall()));
        Log.d("search", String.format("排序类型:%d,优惠券：%s,天猫：%s", searchModel.getSortType(),
                searchModel.isOnlyQuan(), searchModel.isOnlyTmall()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
