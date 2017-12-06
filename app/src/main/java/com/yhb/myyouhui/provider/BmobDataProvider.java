package com.yhb.myyouhui.provider;

import android.util.Log;

import com.yhb.myyouhui.callback.SearchCallback;
import com.yhb.myyouhui.model.CookieModel;
import com.yhb.myyouhui.model.HotKeyModel;
import com.yhb.myyouhui.model.LogModel;
import com.yhb.myyouhui.model.ProductExtraModel;
import com.yhb.myyouhui.model.ProductModel;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.utils.TaoBaoHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by smk on 2017/11/28.
 */

public class BmobDataProvider {
    public static void search(final SearchModel searchModel, final SearchCallback searchCallback) {
        try {

            BmobQuery<ProductModel> query = new BmobQuery<ProductModel>();
            switch (searchModel.getSortType()) {
                case 1:
                    query.order("-order,-tkRate");
                    break;
                case 3:
                    query.order("-order,-zkPrice");
                    break;
                case 4:
                    query.order("-order,zkPrice");
                    break;
                case 9:
                    query.order("-order,-biz30day");
                    break;
                default:
                    query.order("-order,-createdAt");
                    break;
            }

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
//            if (!searchModel.getCategory().equals("tuijian")) {
//                eq3.addWhereEqualTo("sortType", searchModel.getSortType());
//            }

            eq4.addWhereEqualTo("category", searchModel.getCategory());
            andQuerys.add(eq3);
            andQuerys.add(eq4);



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
        } catch (Exception e) {

        }

    }

    public static void setHotKey() {
        try {
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
        } catch (Exception e) {

        }
    }

    public static void loadCookie(final LoadCookieCallBack callBack) {
        try {
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
                    }
                }
            });
        } catch (Exception e) {

        }

    }
    public static void loadHelp(final LoadCookieCallBack callBack) {
        try {
            BmobQuery<CookieModel> bmobQuery = new BmobQuery<CookieModel>();
            bmobQuery.addWhereEqualTo("type", "help");
            bmobQuery.setLimit(1);
            bmobQuery.findObjects(new FindListener<CookieModel>() {
                @Override
                public void done(List<CookieModel> list, BmobException e) {
                    if (e == null) {
                        TaoBaoHelper.GLOABL_COOKIE = list.get(0);
                        if (callBack != null) {
                            callBack.execute(list.get(0));
                        }
                    }
                }
            });
        } catch (Exception e) {

        }

    }
    public static void setFailCookie(CookieModel cookieModel) {
        try {
            cookieModel.setValue("isSuccess", cookieModel.isSuccess());
            cookieModel.setValue("state", "client");
            cookieModel.update(cookieModel.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });
        } catch (Exception e) {

        }
    }

    public interface LoadCookieCallBack {
        void execute(CookieModel cookieModel);
    }

    public static void setProductExtraModel(String auctionId, final ProductExtraModel productExtraModel) {
        try {
            productExtraModel.setAuctionId(auctionId);
            BmobQuery<ProductExtraModel> bmobQuery = new BmobQuery<ProductExtraModel>();
            bmobQuery.addQueryKeys("auctionId,couponLinkTaoToken,couponLink");
            bmobQuery.addWhereEqualTo("auctionId", auctionId);
            bmobQuery.setLimit(1);

            bmobQuery.findObjects(new FindListener<ProductExtraModel>() {
                @Override
                public void done(List<ProductExtraModel> list, BmobException e) {
                    if (e == null && list.size() > 0) {
                        ProductExtraModel item = list.get(0);
                        productExtraModel.setCouponLink(item.getCouponLink());
                        productExtraModel.setCouponLinkTaoToken(item.getCouponLinkTaoToken());

                    }
                }
            });
        } catch (Exception e) {

        }
    }

    public static void updateProductLink(ProductExtraModel productExtraModel) {
        try {
            productExtraModel.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {

                }
            });
        } catch (Exception e) {

        }

    }

    public static void saveLog(LogModel logModel) {
        if (logModel == null) {
            return;
        }
        try {
            logModel.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e != null) {
                        Log.d("bmob", e.getMessage());
                    }
                }
            });
        } catch (Exception e) {

        }
    }
}
