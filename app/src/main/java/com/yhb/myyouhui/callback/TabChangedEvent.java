package com.yhb.myyouhui.callback;

/**
 * Created by smk on 2017/12/1.
 */

public class TabChangedEvent {
    public int getTabPosition() {
        return tabPosition;
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    private int tabPosition;

    public TabChangedEvent(int tabPosition) {
        this.tabPosition = tabPosition;
    }
}
