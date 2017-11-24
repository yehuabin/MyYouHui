package com.yhb.myyouhui.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.yhb.myyouhui.R;
import com.yhb.myyouhui.model.ProductListModel;
import com.yhb.myyouhui.utils.TextUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<ProductListModel.DataBean.PageListBean> data;

    public ProductListAdapter(LayoutInflater inflater, List<ProductListModel.DataBean.PageListBean> data) {

        this.data = data;
        this.inflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_item, null);
        ProductListAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView imageView = (ImageView) holder.rootView.findViewById(R.id.iv_thumb);
        ImageView iv_tmall = (ImageView) holder.rootView.findViewById(R.id.iv_tmall);
        TextView textView = (TextView) holder.rootView.findViewById(R.id.tv_title);
        ProductListModel.DataBean.PageListBean item = data.get(position);
        float realPrice=item.getZkPrice();
        if (item.getCouponStartFee()<=realPrice){
            realPrice=realPrice-item.getCouponAmount();
        }
        textView.setText(item.getTitle().replace("<span class=H>", "").replace("</span>", ""));
        float clientTkRate=item.getTkRate()/2;
        TextView tv_tkRate = (TextView) holder.rootView.findViewById(R.id.tv_tkRate);
        tv_tkRate.setText("返现" + TextUtil.clearZero(clientTkRate) + "%");

        TextView tv_tkCommFee = (TextView) holder.rootView.findViewById(R.id.tv_tkCommFee);
        tv_tkCommFee.setText("奖￥"+ TextUtil.clearZero(String.format("%.1f",realPrice*(clientTkRate/100))));

        TextView tv_biz30day = (TextView) holder.rootView.findViewById(R.id.tv_biz30day);
        tv_biz30day.setText("已售"+ TextUtil.getBiz30day(item.getBiz30day())+"件");

        TextView tv_zkPrice = (TextView) holder.rootView.findViewById(R.id.tv_zkPrice);
        tv_zkPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_zkPrice.setText("￥"+TextUtil.clearZero( item.getZkPrice()));

        TextView tv_couponAmount = (TextView) holder.rootView.findViewById(R.id.tv_couponAmount);
        tv_couponAmount.setText("￥"+ TextUtil.clearZero(item.getCouponAmount()));

        TextView tv_couponLeftCount = (TextView) holder.rootView.findViewById(R.id.tv_couponLeftCount);
        tv_couponLeftCount.setText("剩余"+ item.getCouponLeftCount()+"张");

        TextView tv_realPrice = (TextView) holder.rootView.findViewById(R.id.tv_realPrice);
        tv_realPrice.setText(TextUtil.clearZero(realPrice));

        if (item.getUserType().equals("0")) {
            iv_tmall.setVisibility(View.GONE);
        }
        Glide.with(holder.rootView.getContext()).asBitmap().load("http:" + item.getPictUrl() + "_220x220_.webp")
                .transition(BitmapTransitionOptions.withCrossFade(500))
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
        }
    }
}
