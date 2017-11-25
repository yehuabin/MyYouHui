package com.yhb.myyouhui.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

public class ProductDetailModel {

    /**
     * api : mtop.wdetail.getItemDescx
     * v : 4.1
     * ret : ["SUCCESS::接口调用成功"]
     * data : {"pages":["<img>https://img.alicdn.com/imgextra/i1/3088815683/TB2oJEonRUSMeJjy1zkXXaWmpXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i2/3088815683/TB2_sRujfBNTKJjy0FdXXcPpVXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i2/3088815683/TB2mnwTnHsTMeJjy1zcXXXAgXXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i1/3088815683/TB22P.aXt685uJjSZFsXXX8qVXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i1/3088815683/TB2zQcaXt685uJjSZFsXXX8qVXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i1/3088815683/TB2VZsbXun85uJjSZFvXXXIgXXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i4/3088815683/TB2GKccXsH85uJjSZFqXXa4tpXa_!!3088815683.jpg<\/img>","<img>https://img.alicdn.com/imgextra/i2/3088815683/TB2TDT.XyP85uJjSZFIXXXISXXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i3/3088815683/TB23Btkn3MPMeJjy1XbXXcwxVXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i4/3088815683/TB27La9jPihSKJjy0FeXXbJtpXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i1/3088815683/TB2a4AQi2BNTKJjSszeXXcu2VXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i4/3088815683/TB2IiD.XB_85uJjSZPfXXcp0FXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i3/3088815683/TB2pkIXXrb85uJjSZFmXXcgsFXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i3/3088815683/TB2AAUaXvL85uJjSZFyXXa93XXa_!!3088815683.jpg<\/img>","<img>https://img.alicdn.com/imgextra/i3/3088815683/TB2ycZCnHsTMeJjSszdXXcEupXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i2/3088815683/TB2S.wDi2NNTKJjSspfXXbXIFXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i4/3088815683/TB2Z43Ji2BNTKJjy1zdXXaScpXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i3/3088815683/TB2XI.bXun85uJjSZFvXXXIgXXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i4/3088815683/TB2dqMbXu285uJjSZFwXXc.cVXa_!!3088815683.jpg<\/img><img>https://img.alicdn.com/imgextra/i1/3088815683/TB2ldP.XCr85uJjSZPiXXbOBFXa_!!3088815683.jpg<\/img>"],"images":["https://img.alicdn.com/imgextra/i1/3088815683/TB2oJEonRUSMeJjy1zkXXaWmpXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i2/3088815683/TB2_sRujfBNTKJjy0FdXXcPpVXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i2/3088815683/TB2mnwTnHsTMeJjy1zcXXXAgXXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i1/3088815683/TB22P.aXt685uJjSZFsXXX8qVXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i1/3088815683/TB2zQcaXt685uJjSZFsXXX8qVXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i1/3088815683/TB2VZsbXun85uJjSZFvXXXIgXXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i4/3088815683/TB2GKccXsH85uJjSZFqXXa4tpXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i2/3088815683/TB2TDT.XyP85uJjSZFIXXXISXXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i3/3088815683/TB23Btkn3MPMeJjy1XbXXcwxVXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i4/3088815683/TB27La9jPihSKJjy0FeXXbJtpXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i1/3088815683/TB2a4AQi2BNTKJjSszeXXcu2VXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i4/3088815683/TB2IiD.XB_85uJjSZPfXXcp0FXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i3/3088815683/TB2pkIXXrb85uJjSZFmXXcgsFXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i3/3088815683/TB2AAUaXvL85uJjSZFyXXa93XXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i3/3088815683/TB2ycZCnHsTMeJjSszdXXcEupXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i2/3088815683/TB2S.wDi2NNTKJjSspfXXbXIFXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i4/3088815683/TB2Z43Ji2BNTKJjy1zdXXaScpXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i3/3088815683/TB2XI.bXun85uJjSZFvXXXIgXXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i4/3088815683/TB2dqMbXu285uJjSZFwXXc.cVXa_!!3088815683.jpg","https://img.alicdn.com/imgextra/i1/3088815683/TB2ldP.XCr85uJjSZPiXXbOBFXa_!!3088815683.jpg"]}
     */

    private String api;
    private String v;
    private DataBean data;
    private List<String> ret;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<String> getRet() {
        return ret;
    }

    public void setRet(List<String> ret) {
        this.ret = ret;
    }

    public static class DataBean {
        private List<String> images;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
