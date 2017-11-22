package com.yhb.myyouhui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
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
        ProduceListModel.DataBean.PageListBean bean=new ProduceListModel.DataBean.PageListBean();
        bean.setPictUrl("http://img.alicdn.com/bao/uploaded/i2/240575018/TB2s0_zcwvD8KJjy0FlXXagBFXa_!!240575018.jpg_290x290_.webp");
        for (int i=0;i<50;i++){
            data.add(bean);

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
        ImageView imageView= (ImageView) holder.rootView.findViewById(R.id.iv_thumb);
        TextView textView= (TextView) holder.rootView.findViewById(R.id.tv_title);
        Glide.with(holder.rootView.getContext()).asBitmap().load(data.get(position).getPictUrl())
                .transition(BitmapTransitionOptions.withCrossFade(500))
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public ViewHolder(View itemView) {
            super(itemView);
            rootView=itemView;
        }
    }
}
