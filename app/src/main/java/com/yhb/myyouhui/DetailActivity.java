package com.yhb.myyouhui;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yhb.myyouhui.model.CouponModel;
import com.yhb.myyouhui.model.ProductDetailModel;
import com.yhb.myyouhui.model.ProductExtraModel;
import com.yhb.myyouhui.provider.BmobDataProvider;
import com.yhb.myyouhui.utils.HttpUtil;
import com.yhb.myyouhui.utils.TaoBaoHelper;
import com.yhb.myyouhui.utils.TextUtil;
import com.yhb.myyouhui.views.ResizableImageView;
import com.yhb.myyouhui.views.SmartScrollView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

public class DetailActivity extends BaseActivity {

    //RecyclerView rv_detail;
    boolean loadDetail = false;
    List<String> images;
    SmartScrollView scrollView;
    LinearLayout ll_content;
    boolean isWifi;
    ProductExtraModel productExtraModel=new ProductExtraModel();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView = mViewHolder.get(R.id.scrollView);
        ll_content = mViewHolder.get(R.id.ll_content);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        isWifi = info.getType() == ConnectivityManager.TYPE_WIFI;
        Intent intent = getIntent();
        String realPrice = intent.getStringExtra("realPrice");//券后价
        String title = intent.getStringExtra("title");
        String pictUrl = intent.getStringExtra("pictUrl") + "_600x600_.webp";
        String couponLeftCount = intent.getStringExtra("couponLeftCount");//优惠券剩余
        final String couponAmount = intent.getStringExtra("couponAmount");//优惠券金额
        String zkPrice = intent.getStringExtra("zkPrice");//现价
        int biz30day = intent.getIntExtra("biz30day",0);//已售
        String tkRate = intent.getStringExtra("tkRate");//返现
        final String auctionId = intent.getStringExtra("auctionId");
        float couponStartFee = intent.getFloatExtra("couponStartFee", 0);//满多少使用
        String userType = intent.getStringExtra("userType");//是否天猫
        String commFee = intent.getStringExtra("commFee");//可领红包
        BmobDataProvider.setProductExtraModel(auctionId,productExtraModel);
        if (isWifi) {
            mViewHolder.get(R.id.iv_click).setVisibility(View.GONE);
            mViewHolder.get(R.id.tv_content).setVisibility(View.GONE);
        }
        String detailUrl = "https://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/?data={item_num_id:" + auctionId + "}&type=json&dataType=json";
        HttpUtil.Request(detailUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                ProductDetailModel productDetailModel = gson.fromJson(json, ProductDetailModel.class);
                if (productDetailModel.getRet().size() > 0 && productDetailModel.getRet().get(0).indexOf("SUCCESS") > -1) {
                    images = productDetailModel.getData().getImages();
                    if (isWifi) {

                        loadMore();
                    }
                } else {
                    toastLong("宝贝详情加载失败");
                }
            }
        });


        ImageView iv_tmall = mViewHolder.get(R.id.iv_tmall);
        TextView tv_zkPrice = mViewHolder.get(R.id.tv_zkPrice);

//        rv_detail = mViewHolder.get(rv_detail);
//        rv_detail.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        rv_detail.setHasFixedSize(true);


        tv_zkPrice.setText("现价￥" + zkPrice);
        if (userType.equals("0")) {
            iv_tmall.setVisibility(View.GONE);
        }
        mViewHolder.setText(R.id.tv_title, title);
        mViewHolder.setText(R.id.tv_realPrice, "￥" + realPrice);
        mViewHolder.setText(R.id.tv_biz30day, "已售" + biz30day + "件");
        mViewHolder.setText(R.id.tv_couponAmount, "￥" + couponAmount);
        mViewHolder.setText(R.id.tv_couponStartFee, "(满" + TextUtil.clearZero(couponStartFee) + "使用，剩" + couponLeftCount + "张)");
        mViewHolder.setText(R.id.tv_tkRate, "返现" + tkRate + "%");
        mViewHolder.setText(R.id.tv_tkCommFee, "(按实付款计算，预计￥" + commFee + "元)");
        mViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_back:
                        finish();
                        break;
                    case R.id.broswerBuy:
                        StatService.onEvent(getBaseContext(),"broswerBuy","浏览器打开",1);
                        if (productExtraModel.getCouponLink().length()>0){
                            broswerBuy(productExtraModel.getCouponLink());
                            return;
                        }
                        share(auctionId, new MyCallBack() {
                            @Override
                            public void execute(CouponModel result) {
                                broswerBuy(getCouponLink(result));
                            }
                        });
                        break;
                    case R.id.appBuy:
                        StatService.onEvent(getBaseContext(),"taokouling","淘口令",1);
                        if (productExtraModel.getCouponLinkTaoToken().length()>0){
                            appBuy(productExtraModel.getCouponLinkTaoToken());
                            return;
                        }
                        share(auctionId, new MyCallBack() {
                            @Override
                            public void execute(CouponModel result) {
                                final String link = getCouponLinkTaoToken(result);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                       appBuy(link);
                                    }
                                });
                            }
                        });
                        break;
                    case R.id.ll_content:
                        if (loadDetail) {
                            return;
                        }
                        loadMore();
                        break;
                    default:
                        break;
                }
            }
        }, R.id.iv_back, R.id.broswerBuy, R.id.appBuy, R.id.ll_content);

        mViewHolder.loadImage(getBaseContext(), pictUrl, R.id.iv_picture, R.drawable.detailload);
        if (couponAmount.equals("0")) {
            mViewHolder.get(R.id.ll_quan).setVisibility(View.GONE);
            mViewHolder.get(R.id.iv_quan).setVisibility(View.GONE);
            tv_zkPrice.setText("￥" + zkPrice);
            tv_zkPrice.setTextColor(getResources().getColor(R.color.colorAccent));
            tv_zkPrice.setTextSize(18);
            mViewHolder.get(R.id.ll_quanPrice).setVisibility(View.GONE);
            LinearLayout ll_desc = mViewHolder.get(R.id.ll_desc);
            ViewGroup.LayoutParams layoutParams = ll_desc.getLayoutParams();
            layoutParams.height = 150;
            ll_desc.setLayoutParams(layoutParams);
        } else {
            tv_zkPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }


        final ImageView iv_backtop = mViewHolder.get(R.id.iv_backtop);
        iv_backtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });

        scrollView.setScanScrollChangedListener(new SmartScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {

            }

            @Override
            public void onScrolledToTop() {
                iv_backtop.setVisibility(View.GONE);
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

                if (t > 300) {
                    iv_backtop.setVisibility(View.VISIBLE);
                } else {
                    iv_backtop.setVisibility(View.GONE);
                }
            }
        });
    }

    private void broswerBuy(String link){

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(link);
        intent.setData(content_url);
        startActivity(intent);
    }

    private void appBuy(String link){
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.setText(link);
        toastLong("复制成功，请打开手机淘宝");
    }
    private void loadMore() {
        if (images == null || images.size() == 0) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < images.size(); i++) {
                    final ResizableImageView imageView = new ResizableImageView(getBaseContext());


                    Glide.with(DetailActivity.this).asBitmap().load(images.get(i))

                            .transition(withCrossFade(500))
                            .into(imageView);
                    ll_content.addView(imageView);
                }


                loadDetail = true;
                mViewHolder.get(R.id.iv_click).setVisibility(View.GONE);
                mViewHolder.get(R.id.tv_content).setVisibility(View.GONE);

                if (!isWifi) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView.smoothScrollBy(0, 1450);
                                }
                            });
                        }
                    }, 800);
                }
            }
        });
    }

    private void share(String auctionId, final MyCallBack callBack) {
        if (!TaoBaoHelper.GLOABL_COOKIE.isSuccess()){
            toastLong("服务器忙，请稍后重试");
            BmobDataProvider.loadCookie(null);
            return;
        }
        TaoBaoHelper.generateCoupon(auctionId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastLong("复制失败，请稍后重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                if (response.request().url().host().equals("pub.alimama.com")) {
                    CouponModel couponModel = new Gson().fromJson(json, CouponModel.class);
                    if (couponModel.isOk()) {
                        if(productExtraModel.getAuctionId().length()>0
                                &&(productExtraModel.getCouponLink().length()==0||
                                productExtraModel.getCouponLinkTaoToken().length()==0)){
                            productExtraModel.setCouponLink(getCouponLink(couponModel));
                            productExtraModel.setCouponLinkTaoToken(getCouponLinkTaoToken(couponModel));
                            BmobDataProvider.updateProductLink(productExtraModel);//更新数据库链接信息
                        }
                        callBack.execute(couponModel);
                    }
                } else {
                    toastLong("复制失败，请稍后重试");
                    BmobDataProvider.loadCookie(null);
                }

            }
        });

    }
    private String getCouponLink( CouponModel result){
        return result.getData().getCouponLink() != null &&
                result.getData().getCouponLink().length() > 0 ?
                result.getData().getCouponLink() : result.getData().getClickUrl();

    }
    private String getCouponLinkTaoToken( CouponModel result){
       return result.getData().getCouponLinkTaoToken() != null &&
                result.getData().getCouponLinkTaoToken().length() > 0 ?
                result.getData().getCouponLinkTaoToken() :
                result.getData().getTaoToken();
    }


    public interface MyCallBack {
        void execute(CouponModel result);
    }
}
