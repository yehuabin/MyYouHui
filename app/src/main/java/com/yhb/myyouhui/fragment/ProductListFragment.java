package com.yhb.myyouhui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhb.myyouhui.R;
import com.yhb.myyouhui.adapter.ProductListAdapter;
import com.yhb.myyouhui.utils.LineDecoration;

/**
 * Created by smk on 2017/11/21.
 */

public class ProductListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.product_fragment,container,false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recylerView);
        recyclerView.setAdapter(new ProductListAdapter(inflater,null));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.addItemDecoration(new LineDecoration(inflater.getContext(), LineDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
}
