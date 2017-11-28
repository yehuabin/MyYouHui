package com.yhb.myyouhui.callback;

import com.yhb.myyouhui.model.ProductModel;

import java.util.List;

/**
 * Created by smk on 2017/11/28.
 */

public interface SearchCallback {
    void response(List<ProductModel> data);
}
