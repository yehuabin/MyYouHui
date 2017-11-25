package com.yhb.myyouhui;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yhb.myyouhui.model.ProductDetailModel;
import com.yhb.myyouhui.utils.HttpUtil;
import com.yhb.myyouhui.utils.TextUtil;
import com.yhb.myyouhui.views.ResizableImageView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends BaseActivity {


    boolean loadDetail = false;
    List<String> images;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String realPrice = intent.getStringExtra("realPrice");//券后价
        String title = intent.getStringExtra("title");
        String pictUrl = intent.getStringExtra("pictUrl");
        String couponLeftCount = intent.getStringExtra("couponLeftCount");//优惠券剩余
        String couponAmount = intent.getStringExtra("couponAmount");//优惠券金额
        String zkPrice = intent.getStringExtra("zkPrice");//现价
        String biz30day = intent.getStringExtra("biz30day");//已售
        String tkRate = intent.getStringExtra("tkRate");//返现
        String auctionId = intent.getStringExtra("auctionId");
        float couponStartFee = intent.getFloatExtra("couponStartFee",0);//满多少使用
        String userType= intent.getStringExtra("userType" );//是否天猫
        String commFee= intent.getStringExtra("commFee");//可领红包

        String detailUrl="https://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/?data={item_num_id:"+auctionId+"}&type=json&dataType=json";
        HttpUtil.Request(detailUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Gson gson=new Gson();
                ProductDetailModel productDetailModel= gson.fromJson(json, ProductDetailModel.class);
                if (productDetailModel.getRet().size()>0&&productDetailModel.getRet().get(0).indexOf("SUCCESS")>-1){
                    images=  productDetailModel.getData().getImages();
                }
                else {
                    toastLong("宝贝详情加载失败");
                }
            }
        });
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        final TextView tv_content = (TextView) findViewById(R.id.tv_content);
        ImageView iv_tmall=mViewHolder.get(R.id.iv_tmall);
        TextView tv_zkPrice = mViewHolder.get(R.id.tv_zkPrice);

        tv_zkPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_zkPrice.setText("现价￥" + zkPrice);
        if (userType.equals("0")) {
            iv_tmall.setVisibility(View.GONE);
        }
        mViewHolder.setText(R.id.tv_title,title);
        mViewHolder.setText(R.id.tv_realPrice,"￥"+realPrice);
        mViewHolder.setText(R.id.tv_biz30day,"已售"+biz30day+"件");
        mViewHolder.setText(R.id.tv_couponAmount,"￥"+couponAmount);
        mViewHolder.setText(R.id.tv_couponStartFee,"(满"+ TextUtil.clearZero(couponStartFee)+"使用，剩"+couponLeftCount+"张)");
        mViewHolder.setText(R.id.tv_tkRate,"返现"+tkRate+"%");
        mViewHolder.setText(R.id.tv_tkCommFee, "(按实付款计算，预计￥"+commFee+"元)");
        mViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch (v.getId()){
                   case R.id.iv_back:
                       finish();
                       break;
                   case R.id.broswerBuy:
                       toastLong("打开浏览器");
                       break;
                   case R.id.appBuy:
                       ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                       cm.setText("￥gcTk07tG0nn￥");
                     toastLong("复制成功，请打开手机淘宝");

                       break;
                   case R.id.ll_content:
                       if (loadDetail) {
                           return;
                       }


                       for (int i = 0; i < images.size(); i++) {
                           ResizableImageView imageView = new ResizableImageView(getBaseContext());

                           Glide.with(DetailActivity.this).asBitmap().load(images.get(i))
                                   .into(imageView);
                           ((LinearLayout)v).addView(imageView);
                       }
                       tv_content.setVisibility(View.GONE);
                       ImageView click = (ImageView) findViewById(R.id.iv_click);
                       click.setVisibility(View.GONE);
                       loadDetail = true;
                       break;
               }
            }
        }, R.id.iv_back,R.id.broswerBuy,R.id.appBuy,R.id.ll_content);

        mViewHolder.loadImage(getBaseContext(),pictUrl, R.id.iv_picture);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean isWifi = info.getType() == ConnectivityManager.TYPE_WIFI;
        if (isWifi) {

        } else {

        }
    }
}
