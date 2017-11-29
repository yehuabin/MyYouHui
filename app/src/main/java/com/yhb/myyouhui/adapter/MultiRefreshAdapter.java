package com.yhb.myyouhui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.yhb.myyouhui.DetailActivity;
import com.yhb.myyouhui.R;
import com.yhb.myyouhui.baseadapter.ViewHolder;
import com.yhb.myyouhui.baseadapter.base.MultiBaseAdapter;
import com.yhb.myyouhui.model.ProductModel;
import com.yhb.myyouhui.utils.TextUtil;

import java.util.List;

import static com.yhb.myyouhui.R.id.tv_couponAmount;

/**
 * Author: Othershe
 * Time: 2016/9/9 17:24
 */
public class MultiRefreshAdapter extends MultiBaseAdapter<ProductModel> {

    public MultiRefreshAdapter(Context context, List<ProductModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final ProductModel item, int position, int viewType) {
        ImageView pictView = holder.getView(R.id.iv_thumb);
        ImageView iv_tmall = holder.getView(R.id.iv_tmall);


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
        final String auctionId = item.getAuctionId();
        final float couponStartFee = item.getCouponStartFee();
        final String commFee = TextUtil.clearZero(String.format("%.1f", realPrice * (clientTkRate / 100)));

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
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
                intent.putExtra("auctionId", auctionId);//商品id
                mContext.startActivity(intent);
            }
        });
        holder.setText(R.id.tv_title, title);
        holder.setText(R.id.tv_tkRate, "返现" + TextUtil.clearZero(clientTkRate) + "%");
        holder.setText(R.id.tv_tkCommFee, "返￥" + commFee);
        holder.setText(R.id.tv_biz30day, "已售" + biz30day + "件");
        holder.setText(tv_couponAmount, "￥" + couponAmount);
        holder.setText(R.id.tv_couponLeftCount, "剩余" + couponLeftCount + "张");
        holder.setText(R.id.tv_realPrice, realPriceStr);


        TextView tv_zkPrice = holder.getView(R.id.tv_zkPrice);
        tv_zkPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_zkPrice.setText("￥" + zkPrice);

        if (item.getUserType().equals("0")) {
            iv_tmall.setVisibility(View.GONE);
        }
        else {
            iv_tmall.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).asBitmap().load(pictUrl + "_220x220_.webp")
                .transition(BitmapTransitionOptions.withCrossFade(500))
                .into(pictView);

        if (item.getCouponAmount() == 0) {
            //没有优惠券不显示优惠券文字信息
            holder.getView(R.id.ll_quan).setVisibility(View.GONE);
            holder.getView(R.id.tv_zkPrice).setVisibility(View.GONE);
            holder.setText(R.id.tv_realPriceDesc, "现价￥");
            holder.getView(R.id.tv_zkPriceDesc).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.ll_quan).setVisibility(View.VISIBLE);
            holder.getView(R.id.tv_zkPrice).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_realPriceDesc, "券后价￥");
            holder.getView(R.id.tv_zkPriceDesc).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getItemLayoutId(int viewType) {
       return R.layout.product_item;
    }

    @Override
    protected int getViewType(int position, ProductModel data) {
        return 0;
    }

}
