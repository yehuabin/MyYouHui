package com.yhb.myyouhui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yhb.myyouhui.BaseActivity;
import com.yhb.myyouhui.R;
import com.yhb.myyouhui.fragment.ProductListFragment;

public class ResultActivity extends BaseActivity {

    RecyclerView recyclerView;
    ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewHolder.setText(R.id.et_search, getIntent().getStringExtra("keyword"));
        mViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_back:
                        finish();
                        break;
                    case R.id.et_search:
                        finish();
                        break;
                }
            }
        }, R.id.iv_back, R.id.et_search);

        Intent intent = getIntent();
        final String keyword = intent.getStringExtra("keyword");
        final ProductListFragment productListFragment = new ProductListFragment();
        viewPager = mViewHolder.get(R.id.viewPage);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("keyword", keyword);
                productListFragment.setArguments(bundle);
                return productListFragment;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
    }
}
