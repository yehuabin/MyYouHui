package com.yhb.myyouhui.provider;

import android.util.Log;

import com.yhb.myyouhui.callback.SearchCallback;
import com.yhb.myyouhui.model.CookieModel;
import com.yhb.myyouhui.model.HotKeyModel;
import com.yhb.myyouhui.model.ProductModel;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.utils.TaoBaoHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by smk on 2017/11/28.
 */

public class BmobDataProvider {
    public static void search(final SearchModel searchModel, final SearchCallback searchCallback) {

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
            eq2.addWhereEqualTo("userType", "1");
            andQuerys.add(eq2);
        }
        eq3.addWhereEqualTo("sortType", searchModel.getSortType());
        eq4.addWhereEqualTo("category", searchModel.getCategory());
        andQuerys.add(eq3);
        andQuerys.add(eq4);


        BmobQuery<ProductModel> query = new BmobQuery<ProductModel>();
        query.and(andQuerys);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(SearchModel.PAGE_SIZE);
        query.setSkip(searchModel.getPage() * SearchModel.PAGE_SIZE);
        //执行查询方法
        query.findObjects(new FindListener<ProductModel>() {
            @Override
            public void done(List<ProductModel> object, BmobException e) {

                searchCallback.response(object, e == null);


            }
        });

    }

    public static void setHotKey() {
        BmobQuery<HotKeyModel> query = new BmobQuery<HotKeyModel>();
        query.order("-createdAt");
        //执行查询方法
        query.findObjects(new FindListener<HotKeyModel>() {
            @Override
            public void done(List<HotKeyModel> object, BmobException e) {

                if (e == null) {
                    SearchModel.HOTKEY_LIST = object;
                }


            }
        });
    }

    public static void loadCookie(final LoadCookieCallBack callBack) {
        BmobQuery<CookieModel> bmobQuery = new BmobQuery<CookieModel>();
        bmobQuery.addWhereEqualTo("type", "token");
        bmobQuery.setLimit(1);
        bmobQuery.findObjects(new FindListener<CookieModel>() {
            @Override
            public void done(List<CookieModel> list, BmobException e) {
                if (e == null) {
                    TaoBaoHelper.GLOABL_COOKIE = list.get(0);
                    if (callBack != null) {
                        callBack.execute(list.get(0));
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    public interface LoadCookieCallBack {
        void execute(CookieModel cookieModel);
    }
}
