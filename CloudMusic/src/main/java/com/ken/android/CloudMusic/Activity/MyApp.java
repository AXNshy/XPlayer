package com.ken.android.CloudMusic.Activity;

import android.app.Application;

import com.ken.android.CloudMusic.DBHelper.MusicInfoDao;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by axnshy on 16/8/5.
 */
public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        scanLocal();
    }
    /*
   * 扫描本地音乐文件
   * */
    private void scanLocal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MusicInfoDao.scanMusic(getApplicationContext());
            }
        }).start();

    }
}
