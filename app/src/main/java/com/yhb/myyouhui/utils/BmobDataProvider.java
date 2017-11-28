package com.yhb.myyouhui.utils;

import android.util.Log;

import com.yhb.myyouhui.callback.SearchCallback;
import com.yhb.myyouhui.model.ProductModel;
import com.yhb.myyouhui.model.SearchModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by smk on 2017/11/28.
 */

public class BmobDataProvider {
    public static void search(SearchModel searchModel, final SearchCallback searchCallback) {

        BmobQuery<ProductModel> eq1 = new BmobQuery<ProductModel>();
        BmobQuery<ProductModel> eq2 = new BmobQuery<ProductModel>();
        BmobQuery<ProductModel> eq3 = new BmobQuery<ProductModel>();
        BmobQuery<ProductModel> eq4 = new BmobQuery<ProductModel>();
        List<BmobQuery<ProductModel>> andQuerys = new ArrayList<BmobQuery<ProductModel>>();
        if (searchModel.isOnlyQuan()) {
            eq1.addWhereGreaterThan("couponAmount", 0);
            andQuerys.add(eq1);
        }
        if (searchModel.isOnlyTmall()) {
            eq2.addWhereEqualTo("userType", 1);
            andQuerys.add(eq2);
        }
        eq3.addWhereEqualTo("sortType", searchModel.getSortType());
        eq4.addWhereEqualTo("category", searchModel.getCategory());
        andQuerys.add(eq3);
        andQuerys.add(eq4);


        BmobQuery<ProductModel> query = new BmobQuery<ProductModel>();
        query.and(andQuerys);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(20);
        query.setSkip(searchModel.getPage() * 20);
        //执行查询方法
        query.findObjects(new FindListener<ProductModel>() {
            @Override
            public void done(List<ProductModel> object, BmobException e) {
                if (e == null) {
                    searchCallback.response(object);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }
}
