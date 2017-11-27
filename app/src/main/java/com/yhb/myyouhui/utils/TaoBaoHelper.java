package com.yhb.myyouhui.utils;

import java.io.IOException;

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
    private static  String cookie = "19741e6c14bb32db41d5e14450885bf0";
    private static  String searchCookie = "frKiEpBaJFECAT2kgLpjUqMb";
    public static void generateCoupon(String auctionId,Callback callback){
        OkHttpClient okHttpClient = getClient("cookie2",getCookie());

        Request request = new Request.Builder().url("http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+auctionId+"&adzoneid=148716480&siteid=39748344&scenes=1").build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void seachNZJH(Callback callback){
        OkHttpClient okHttpClient = getClient("cna",searchCookie);

        Request request = new Request.Builder().url(UrlUtil.getSearchUrl("")).build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void isLogin(Callback callback){
        OkHttpClient okHttpClient = getClient("cookie2",getCookie());
        Request request = new Request.Builder().url("http://pub.alimama.com/common/getUnionPubContextInfo.json").build();
        okHttpClient.newCall(request).enqueue(callback);
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


    private static String getCookie(){
        return cookie;
    }
    public static void setCookie(String val){
        cookie=val;
    }

    public static String getLoginUrl(){
        return "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=alimama&from=alimama&redirectURL=http://pub.alimama.com/common/code/getAuctionCode.json?auctionid=556095016857&adzoneid=148716480&siteid=39748344&scenes=1&full_redirect=true&disableQuickLogin=true";
    }
}
