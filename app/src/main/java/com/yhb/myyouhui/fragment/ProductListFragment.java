package com.yhb.myyouhui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yhb.myyouhui.R;
import com.yhb.myyouhui.adapter.ProductListAdapter;
import com.yhb.myyouhui.callback.SearchCallback;
import com.yhb.myyouhui.model.ProductModel;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.utils.BmobDataProvider;
import com.yhb.myyouhui.utils.CategoryUtil;
import com.yhb.myyouhui.utils.LineDecoration;

import java.util.List;

/**
 * Created by smk on 2017/11/21.
 */

public class ProductListFragment extends Fragment {
    RecyclerView recyclerView;
    LayoutInflater inflater;
    ProductListAdapter adapter;
    TabLayout tl_sortType;
    CheckBox ck_onlyQuan;
    CheckBox ck_onlyTmall;
    private int lastVisibleItem = 0;
    SearchModel searchModel = new SearchModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        this.inflater = inflater;
        Bundle bundle = getArguments();
        String type = bundle.getString("type");
        int position = bundle.getInt("position");
        if (type == "index") {
            searchModel.setCategory(CategoryUtil.getVal(String.valueOf(position)));
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.addItemDecoration(new LineDecoration(inflater.getContext(), LineDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !adapter.isLoadOver()) {
                    searchModel.setPage(searchModel.getPage() + 1);


                    loadData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        loadData();
        TextView tv_onlyQuan = (TextView) view.findViewById(R.id.tv_onlyQuan);
        Drawable img = getResources().getDrawable(R.drawable.quan);
        img.setBounds(0, 0, 50, 50);
        tv_onlyQuan.setCompoundDrawables(img, null, null, null);

        TextView tv_onlyTmall = (TextView) view.findViewById(R.id.tv_onlyTmall);
        img = getResources().getDrawable(R.drawable.tmall);
        img.setBounds(0, 0, 40, 40);
        tv_onlyTmall.setCompoundDrawables(img, null, null, null);

        tl_sortType = (TabLayout) view.findViewById(R.id.tl_sortType);
        ck_onlyQuan = (CheckBox) view.findViewById(R.id.ck_onlyQuan);
        ck_onlyTmall = (CheckBox) view.findViewById(R.id.ck_onlyTmall);
        ck_onlyQuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSortType();
                search();
            }
        });
        ck_onlyTmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSortType();
                search();
            }
        });
        tl_sortType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSortType();
                search();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private int sortType = 0;

    private void setSortType() {
        if (tl_sortType.getSelectedTabPosition() == 0) {
            sortType = 0;
        } else if (tl_sortType.getSelectedTabPosition() == 1) {
            sortType = 1;
        } else if (tl_sortType.getSelectedTabPosition() == 2) {
            sortType = 9;
        } else if (tl_sortType.getSelectedTabPosition() == 3) {
            sortType = 3;
        }
    }

    private void search() {

        adapter.setEmpty();
        searchModel.setSortType(sortType);
        searchModel.setOnlyQuan(ck_onlyQuan.isChecked());
        searchModel.setOnlyTmall(ck_onlyTmall.isChecked());
        searchModel.setPage(0);
        adapter.clear();
        loadData();

    }

    private void loadData() {

        BmobDataProvider.search(searchModel, new SearchCallback() {
            @Override
            public void response(final List<ProductModel> data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data.size() == 0) {
                            adapter.noMore();
                            return;
                        }
                        if (searchModel.getPage() == 0) {
                            adapter = new ProductListAdapter(inflater, data);
                            recyclerView.setAdapter(adapter);
                        } else {
                            adapter.addMore(data);
                        }
                    }

                });
            }
        });
    }
}
