package com.yhb.myyouhui.utils;

/**
 * Created by Administrator on 2017/11/26.
 */

public class UrlUtil {
    public static String getSearchUrl(int page,String keyword){
        return "http://pub.alimama.com/items/search.json?toPage="+page+"&queryType=2&perPageSize=20&q="+keyword;
    }
    public static String getSearchUrl(String keyword){
        return getSearchUrl(1,keyword);
    }
    public static String getNZJH() {
        String url = "http://pub.alimama.com/items/channel/nzjh.json?channel=nzjh&perPageSize=12&shopTag=&toPage=1";
        return url;
    }
}
