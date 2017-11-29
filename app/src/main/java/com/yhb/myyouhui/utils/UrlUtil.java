package com.yhb.myyouhui.utils;

import android.util.Log;

import com.yhb.myyouhui.model.SearchModel;

/**
 * Created by Administrator on 2017/11/26.
 */

public class UrlUtil {

    public static String getSearchUrl(SearchModel searchModel) {
        String showTag = "";
        String sortType = String.valueOf(searchModel.getSortType());
        if (searchModel.isOnlyQuan() && searchModel.isOnlyTmall()) {
            showTag = "b2c,dpyhq";
        } else if (searchModel.isOnlyQuan()) {
            showTag = "dpyhq";
        } else if (searchModel.isOnlyTmall()) {
            showTag = "b2c";
        }

      if (searchModel.getSortType() == 3) {
            sortType = "3&startBiz30day=100";
        }
        String url = String.format("http://pub.alimama.com/items/search.json?toPage=%s&perPageSize=%s&q=%s&shopTag=%s&sortType=%s&queryType=0",
                searchModel.getPage() + 1, SearchModel.PAGE_SIZE, searchModel.getKeyword(), showTag, sortType);
        Log.d("bmob", "getSearchUrl: " + url);
        return url;
    }

}
