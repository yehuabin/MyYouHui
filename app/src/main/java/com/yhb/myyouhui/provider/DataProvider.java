package com.yhb.myyouhui.provider;

import com.yhb.myyouhui.callback.SearchCallback;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.utils.TaoBaoHelper;

/**
 * Created by Administrator on 2017/11/29.
 */

public class DataProvider {
    public static void search(final SearchModel searchModel, final SearchCallback searchCallback) {

        if (searchModel.getSearchType().equals("search")) {
            //调用淘宝搜索
            TaoBaoHelper.search(searchModel, searchCallback);
        } else {
            BmobDataProvider.search(searchModel, searchCallback);
        }

    }
}
