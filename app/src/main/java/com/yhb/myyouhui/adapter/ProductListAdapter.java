package com.yhb.myyouhui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhb.myyouhui.R;
import com.yhb.myyouhui.model.ProduceListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<ProduceListModel.DataBean.PageListBean> data;

    public ProductListAdapter(LayoutInflater inflater, List<ProduceListModel.DataBean.PageListBean> data) {
        data=new ArrayList<>();
        for (int i=0;i<50;i++){
            data.add(new ProduceListModel.DataBean.PageListBean());

        }
        this.data = data;
        this.inflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.product_item,null);
        ProductListAdapter.ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
