package com.yoyohan.umengutil;

import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.game.UMGameAgent;

public class MainActivity {

    public void setUmengPageMode(int id) {
        Log.i("Unity", "设置页面收集方式setUmengPageMode:" + id);
        if (id == 0) {
            UMGameAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        } else if (id == 1) {
            UMGameAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);
        } else if (id == 2) {
            //默认是这种形式
            UMGameAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_AUTO);
        } else if (id == 3) {
            //只有这种  不统计到宿主Activity的页面路径数据
            UMGameAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
        }
    }
}
