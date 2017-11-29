package com.yhb.myyouhui.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smk on 2017/11/29.
 */

public class CategoryUtil {
    private static Map<String, String[]> maps = new HashMap<>();

    static {
        maps.put("0", new String[]{"女装", "nzjh"});
        maps.put("1", new String[]{"男装", "nanz"});
        maps.put("2", new String[]{"母婴", "muying"});
        maps.put("3", new String[]{"食品", "hch"});
        maps.put("4", new String[]{"家居", "jyj"});
        maps.put("5", new String[]{"亲宝贝", "qbb"});
        maps.put("6", new String[]{"运动户外", "kdc"});
        maps.put("7", new String[]{"时尚", "ifs"});
        maps.put("8", new String[]{"9块9", "9k9"});
        maps.put("9", new String[]{"20元封顶", "20k"});
        maps.put("10", new String[]{"特价好货", "tehui"});
        maps.put("11", new String[]{"淘宝DIY", "diy"});
        /*
        *  tab_category.addTab(tab_category.newTab().setText("女装"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=nzjh&toPage=1
        tab_category.addTab(tab_category.newTab().setText("男装"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=ifs&toPage=1&catIds=30&level=1
        tab_category.addTab(tab_category.newTab().setText("母婴"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=muying
        tab_category.addTab(tab_category.newTab().setText("食品"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=hch
        tab_category.addTab(tab_category.newTab().setText("家居"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=jyj
        tab_category.addTab(tab_category.newTab().setText("亲宝贝"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=qbb
        tab_category.addTab(tab_category.newTab().setText("运动户外"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=kdc
        tab_category.addTab(tab_category.newTab().setText("时尚"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=ifs
        tab_category.addTab(tab_category.newTab().setText("9块9"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=9k9
        tab_category.addTab(tab_category.newTab().setText("20元封顶"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=20k
        tab_category.addTab(tab_category.newTab().setText("特价好货"));//http://pub.alimama.com/promo/item/channel/index.htm?channel=tehui
        tab_category.addTab(tab_category.newTab().setText("淘宝DIY"));//http://pub.alimama.com/promo/item/oe_channel/index.htm?channel=diy
*/
    }

    public static String getVal(String key) {
        String val = "";
        for (Map.Entry<String, String[]> m : maps.entrySet()) {
            if (m.getKey().equals( key)) {
                val = m.getValue()[1];
                break;
            }
        }
        return val;
    }
    public static String getText(String key) {
        String val = "";
        for (Map.Entry<String, String[]> m : maps.entrySet()) {
            if (m.getKey().equals( key)) {
                val = m.getValue()[0];
                break;
            }
        }
        return val;
    }

    public static int getCount(){
        return maps.size();
    }
}
