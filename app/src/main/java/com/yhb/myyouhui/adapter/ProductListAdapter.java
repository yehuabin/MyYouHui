package com.yhb.myyouhui.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.yhb.myyouhui.DetailActivity;
import com.yhb.myyouhui.R;
import com.yhb.myyouhui.model.ProductListModel;
import com.yhb.myyouhui.utils.TextUtil;

import java.util.List;

import static com.yhb.myyouhui.R.id.tv_couponAmount;

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

        ImageView pictView = holder.get(R.id.iv_thumb);
        ImageView iv_tmall = holder.get(R.id.iv_tmall);

        ProductListModel.DataBean.PageListBean item = data.get(position);
        float realPrice = item.getZkPrice();
        if (item.getCouponStartFee() <= realPrice) {
            realPrice = realPrice - item.getCouponAmount();
        }
        final String title = item.getTitle().replace("<span class=H>", "").replace("</span>", "");
        final float clientTkRate = item.getTkRate() / 2;
        final String biz30day = TextUtil.getBiz30day(item.getBiz30day());
        final String zkPrice = TextUtil.clearZero(item.getZkPrice());
        final String couponAmount = TextUtil.clearZero(item.getCouponAmount());
        final String couponLeftCount = item.getCouponLeftCount();
        final String realPriceStr = TextUtil.clearZero(realPrice);
        final String userType = item.getUserType();
        final String pictUrl = "http:" + item.getPictUrl();
        final String auctionId=item.getAuctionId();
        final float couponStartFee=item.getCouponStartFee();
        final String commFee=TextUtil.clearZero(String.format("%.1f", realPrice * (clientTkRate / 100)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), DetailActivity.class);
                intent.putExtra("realPrice", realPriceStr);//券后价
                intent.putExtra("pictUrl", pictUrl);
                intent.putExtra("title", title);
                intent.putExtra("couponLeftCount", couponLeftCount);//优惠券剩余
                intent.putExtra("couponAmount", couponAmount);//优惠券金额
                intent.putExtra("zkPrice", zkPrice);//现价
                intent.putExtra("biz30day", biz30day);//已售
                intent.putExtra("tkRate", TextUtil.clearZero(clientTkRate));//返现
                intent.putExtra("couponStartFee", couponStartFee);//满多少使用
                intent.putExtra("userType", userType);//是否天猫
                intent.putExtra("commFee", commFee);//可领红包
                intent.putExtra("auctionId", auctionId);//可领红包
                inflater.getContext().startActivity(intent);
            }
        });
        holder.setText(R.id.tv_title, title);
        holder.setText(R.id.tv_tkRate, "返现" + TextUtil.clearZero(clientTkRate) + "%");
        holder.setText(R.id.tv_tkCommFee, "奖￥" + commFee);
        holder.setText(R.id.tv_biz30day, "已售" + biz30day + "件");
        holder.setText(tv_couponAmount, "￥" + couponAmount);
        holder.setText(R.id.tv_couponLeftCount, "剩余" + couponLeftCount + "张");
        holder.setText(R.id.tv_realPrice, realPriceStr);

        TextView tv_zkPrice = holder.get(R.id.tv_zkPrice);
        tv_zkPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_zkPrice.setText("￥" + zkPrice);

        if (item.getUserType().equals("0")) {
            iv_tmall.setVisibility(View.GONE);
        }
        Glide.with(holder.rootView.getContext()).asBitmap().load(pictUrl + "_220x220_.webp")
                .transition(BitmapTransitionOptions.withCrossFade(500))
                .into(pictView);

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


        public View getRootView() {
            return rootView;
        }

        private final SparseArray<View> mViews = new SparseArray<View>();

        private <T extends View> T bindView(int id) {
            T view = (T) mViews.get(id);
            if (view == null) {
                view = (T) rootView.findViewById(id);
                mViews.put(id, view);
            }
            return view;
        }

        public <T extends View> T get(int id) {
            return (T) bindView(id);
        }

        public void setOnClickListener(View.OnClickListener l, int... ids) {
            if (ids == null) {
                return;
            }
            for (int id : ids) {
                get(id).setOnClickListener(l);
            }
        }

        public void setText(int id, String text) {
            TextView textView = get(id);
            textView.setText(text);
        }
    }
}
