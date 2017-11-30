package com.yhb.myyouhui.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/30.
 */

public class HotKeyModel extends BmobObject {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
