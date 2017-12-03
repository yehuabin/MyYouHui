package com.yhb.myyouhui.utils;

import com.google.gson.Gson;
import com.yhb.myyouhui.callback.SearchCallback;
import com.yhb.myyouhui.model.CookieModel;
import com.yhb.myyouhui.model.ProductListModel;
import com.yhb.myyouhui.model.ProductModel;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.provider.BmobDataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by smk on 2017/11/24.
 */

public class TaoBaoHelper {
    private static final String TAG = "TaoBaoHelper";

    public static CookieModel GLOABL_COOKIE=new CookieModel() ;
    //private static  String searchCookie = "frKiEpBaJFECAT2kgLpjUqMb";
    public static void generateCoupon(String auctionId,Callback callback){
        OkHttpClient okHttpClient = getClient("cookie2",getCookie());

        Request request = new Request.Builder().url("http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+auctionId+"&adzoneid=148716480&siteid=39748344&scenes=1").build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void search(final SearchModel searchModel, final SearchCallback searchCallback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(UrlUtil.getSearchUrl(searchModel)).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              try{
                  String json = response.body().string();

                  Gson gson = new Gson();
                  final List<ProductListModel.DataBean.PageListBean> datas = gson.fromJson(json, ProductListModel.class).getData().getPageList();
                  List<ProductModel> result=new ArrayList<ProductModel>();
                  if (datas==null||datas.size()==0){
                      searchCallback.response(result,true);
                      return;
                  }
                  for (int i=0;i<datas.size();i++){
                      result.add(ModelUtil.getProductModel(datas.get(i),searchModel.getCategory(),searchModel.getSortType()));
                  }
                  searchCallback.response(result,true);
              }
              catch (Exception e){
                  searchCallback.response(null,false);
              }

            }
        });
    }

    public static void isLogin(Callback callback){
        OkHttpClient okHttpClient = getClient("cookie2",getCookie());
        Request request = new Request.Builder().url("http://pub.alimama.com/common/getUnionPubContextInfo.json").build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static String getCookie(){
        if (GLOABL_COOKIE==null){
            return "";
        }
        return GLOABL_COOKIE.getCookie();
    }

    private static OkHttpClient getClient(final String cookieKey, final String cookieVal){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request original = chain.request();

                        final Request authorized = original.newBuilder()
                                .addHeader("Cookie", cookieKey+"="+cookieVal)
                                .build();

                        return chain.proceed(authorized);
                    }
                })
                .build();
        return okHttpClient;
    }

public static void loadCookie(BmobDataProvider.LoadCookieCallBack callBack){

    BmobDataProvider.loadCookie(callBack);
}


    public static String getLoginUrl(){
        return "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=alimama&from=alimama&redirectURL=http://pub.alimama.com/common/code/getAuctionCode.json?auctionid=556095016857&adzoneid=148716480&siteid=39748344&scenes=1&full_redirect=true&disableQuickLogin=true";
    }
}
