package com.yhb.myyouhui.model;

/**
 * Created by smk on 2017/11/27.
 */

public class SearchModel {
    public static int ZONGHE=0;//综合排序
    public static int FANXIAN=1;//返现比率排序
    public static int JIAGE=2;//价格升序
    public static int JIAGEDESC=3;//价格降序
    public static int XIAOLIANG=4;//销量降序
    private int sortType;
    private boolean onlyQuan;
    private boolean onlyTmall;

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public boolean isOnlyQuan() {
        return onlyQuan;
    }

    public void setOnlyQuan(boolean onlyQuan) {
        this.onlyQuan = onlyQuan;
    }

    public boolean isOnlyTmall() {
        return onlyTmall;
    }

    public void setOnlyTmall(boolean onlyTmall) {
        this.onlyTmall = onlyTmall;
    }
}
