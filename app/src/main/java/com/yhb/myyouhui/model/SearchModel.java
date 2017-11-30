package com.yhb.myyouhui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smk on 2017/11/27.
 */

public class SearchModel {
    public static List<HotKeyModel> HOTKEY_LIST=new ArrayList<>();
    public static int PAGE_SIZE=12;
    public static int ZONGHE=0;//综合排序
    public static int FANXIAN=1;//返现比率排序
    public static int JIAGE_SHENG=4;//价格升序
    public static int JIAGE_JIANG=3;//价格降序
    public static int XIAOLIANG=9;//销量降序
    private int sortType;
    private String searchType;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private String keyword;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private int page;
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
