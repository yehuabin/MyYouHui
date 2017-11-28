package com.yhb.myyouhui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yhb.myyouhui.R;
import com.yhb.myyouhui.adapter.ProductListAdapter;
import com.yhb.myyouhui.model.ProductListModel;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.utils.LineDecoration;
import com.yhb.myyouhui.utils.TaoBaoHelper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by smk on 2017/11/21.
 */

public class ProductListFragment extends Fragment {
    RecyclerView recyclerView;
    LayoutInflater inflater;

    TabLayout tl_sortType;
    CheckBox ck_onlyQuan;
    CheckBox ck_onlyTmall;
    SearchModel searchModel = new SearchModel();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        this.inflater = inflater;
        Bundle bundle= getArguments();

        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.addItemDecoration(new LineDecoration(inflater.getContext(), LineDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(linearLayoutManager);
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
                search();
            }
        });
        ck_onlyTmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                search();
            }
        });
        tl_sortType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
    private void search() {
        searchModel.setSortType(tl_sortType.getSelectedTabPosition());
        searchModel.setOnlyQuan(ck_onlyQuan.isChecked());
        searchModel.setOnlyTmall(ck_onlyTmall.isChecked());


        Log.d("search", String.format("排序类型:%d,优惠券：%s,天猫：%s", searchModel.getSortType(),
                searchModel.isOnlyQuan(), searchModel.isOnlyTmall()));
    }
    private void loadData() {
        TaoBaoHelper.seachNZJH(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                Gson gson = new Gson();
                final List<ProductListModel.DataBean.PageListBean> datas = gson.fromJson(json, ProductListModel.class).getData().getPageList();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView.setAdapter(new ProductListAdapter(inflater, datas));

                    }

                });

            }
        });

    }
}
