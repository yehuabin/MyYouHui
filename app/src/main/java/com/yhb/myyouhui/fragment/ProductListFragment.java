package com.yhb.myyouhui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yhb.myyouhui.R;
import com.yhb.myyouhui.adapter.ProductListAdapter;
import com.yhb.myyouhui.model.ProductListModel;
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
        return view;
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
