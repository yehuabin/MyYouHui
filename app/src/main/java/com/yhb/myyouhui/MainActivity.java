package com.yhb.myyouhui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.baidu.mobstat.StatService;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.yhb.myyouhui.fragment.ProductListFragment;
import com.yhb.myyouhui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {

    TabLayout tab_category;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    ViewPager vp_list;

    List<ProductListFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "9be40913fa1a1f940dc81aafa1139757");
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(MainActivity.this,
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {

                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("更新")
                                .setMessage("")
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                startDownloadTask(
                                                        MainActivity.this,
                                                        appBean.getDownloadURL());
                                            }
                                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });
        StatService.start(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        tab_category = mViewHolder.get(R.id.tab_category);
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

        tab_category.addTab(tab_category.newTab().setText("女装"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=nzjh&toPage=1
        tab_category.addTab(tab_category.newTab().setText("男装"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=ifs&toPage=1&catIds=30&level=1
        tab_category.addTab(tab_category.newTab().setText("母婴"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=muying
        tab_category.addTab(tab_category.newTab().setText("食品"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=hch
        tab_category.addTab(tab_category.newTab().setText("家居"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=jyj
        tab_category.addTab(tab_category.newTab().setText("亲宝贝"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=qbb
        tab_category.addTab(tab_category.newTab().setText("运动户外"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=kdc
        tab_category.addTab(tab_category.newTab().setText("时尚"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=ifs
        tab_category.addTab(tab_category.newTab().setText("9块9"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=9k9
        tab_category.addTab(tab_category.newTab().setText("20元封顶"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=20k
        tab_category.addTab(tab_category.newTab().setText("特价好货"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=tehui
        tab_category.addTab(tab_category.newTab().setText("淘宝DIY"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=diy

        tab_category.setTabMode(TabLayout.MODE_SCROLLABLE);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
