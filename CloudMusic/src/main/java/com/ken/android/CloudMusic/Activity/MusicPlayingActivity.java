package com.ken.android.CloudMusic.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ken.android.CloudMusic.DBHelper.MusicInfoDao;
import com.ken.android.CloudMusic.FilesRead.MusicInfo;
import com.ken.android.CloudMusic.Fragment.AlbumDetailFragment;
import com.ken.android.CloudMusic.PlayerService;
import com.ken.android.CloudMusic.R;
import com.ken.android.CloudMusic.Service.Service;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by axnshy on 16/7/29.
 */
@ContentView(R.layout.music_play)
public class MusicPlayingActivity extends BaseActivity implements Observer {

    @ViewInject(R.id.tb_musicplaying_top)
    private Toolbar toolbar;
    @ViewInject(R.id.iv_back)
    private ImageView returnImg;
    @ViewInject(R.id.tv_viewTitle)
    private TextView toolbar_title;
    @ViewInject(R.id.progressbar_player_status)
    private SeekBar playStatusBar;
    @ViewInject(R.id.tv_musicPlay_currentLocation)
    private TextView playStatusCurrent;
    @ViewInject(R.id.tv_musicPlay_totalTime)
    private TextView playStatusTotal;
    @ViewInject(R.id.iv_repeat_play)
    private ImageView musicRepeatImg;
    @ViewInject(R.id.iv_previous_play)
    private ImageView musicPrevImg;
    @ViewInject(R.id.iv_play)
    private ImageView musicPlayImg;
    @ViewInject(R.id.iv_next_play)
    private ImageView musicNextImg;
    @ViewInject(R.id.iv_shuffle_play)
    private ImageView musicShuffleImg;
    @ViewInject(R.id.iv_current_musicAlbumCover)
    private ImageView artWorkImg;
    @ViewInject(R.id.tv_current_musicName)
    private TextView CurrentMusicNameTx;
    @ViewInject(R.id.tv_current_musicArtist)
    private TextView CurrentArtWorkTx;

    private Context context;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                playStatusBar.setProgress((int) msg.obj);
                    playStatusCurrent.setText(MusicInfo.parseMusicData(mService.getPlayerInstance().getCurrentPosition()));
                    break;
                case 1:  Bitmap bm = BitmapFactory.decodeFile((String) msg.obj);
                    BitmapDrawable bmpDraw = new BitmapDrawable(bm);
                    artWorkImg.setImageDrawable(bmpDraw);
                    break;
            }
        }
    };

    @Event(value = {R.id.iv_back, R.id.iv_toolbar_music_share, R.id.iv_repeat_play, R.id.iv_previous_play, R.id.iv_play, R.id.iv_next_play, R.id.iv_shuffle_play})
    private void OnClicked(View view) {
        //判断Service是否存在

        if (!Service.isMyServiceRunning(context, PlayerService.class)) {
            Intent serviceintent = new Intent(context, PlayerService.class);
            startService(serviceintent);
            Log.e("TAGS", "MediaPlayerService does not existed");
        } else {
            Log.e("TAGS", "MediaPlayerService existed");

        }
        switch (view.getId()) {
            case R.id.iv_repeat_play:
                if (mService.repeatTag == 0) {
                    musicRepeatImg.setBackgroundResource(R.drawable.basecopy);
                    mService.repeatTag = 1;
                } else {
                    musicRepeatImg.setBackgroundResource(R.drawable.base);
                    mService.repeatTag = 0;
                }
                break;
            case R.id.iv_shuffle_play:
                if (mService.shuffleTag == 0) {
                    musicShuffleImg.setBackgroundResource(R.drawable.basecopy);
                    mService.shuffleTag = 1;
                } else {
                    musicShuffleImg.setBackgroundResource(R.drawable.base);
                    mService.shuffleTag = 0;
                }
                break;
            case R.id.iv_previous_play:
                int location = mService.getMyList().indexOf(mService.currentMusic);
                if (location == 0) {
                    mService.play(mService.getMyList().get(mService.getMyList().size() - 1));
                } else
                    mService.play(mService.getMyList().get(location - 1));
                break;
            case R.id.iv_next_play:
                int location1 = mService.getMyList().indexOf(mService.currentMusic);
                if (location1 == mService.getMyList().size() - 1) {
                    mService.play(mService.getMyList().get(0));
                } else
                    mService.play(mService.getMyList().get(location1 + 1));
                break;
            case R.id.iv_play: {
                if (mService.getPlayerState() == mService.MediaPlayer_PLAY) {
                    mService.pause();
                    mService.setPlayerState(mService.MediaPlayer_PAUSE);
                } else {
                    mService.getPlayerInstance().start();
                    mService.setPlayerState(mService.MediaPlayer_PLAY);
                }
                break;
            }
            case R.id.iv_back:
                onBackPressed();
                break;
        }
        updateUI();
    }

    //绑定service与activity
    private PlayerService mService;

    // 定义ServiceConnection
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 通过定义的Binder来获取Service实例来供使用
            mService = ((PlayerService.MyBinder) service).getService();
            updateUI();
            Log.w(PlayerService.LOG_TAG, "Activity onServiceConnected");
            mService.addObserver(MusicPlayingActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            // 当Service被意外销毁时
            Log.w(PlayerService.LOG_TAG, "Activity onServiceDisconnected");
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        // bindService
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Log.w(PlayerService.LOG_TAG, "Activity bindService");
        // updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 进行unbind
        unbindService(conn);
        Log.w(PlayerService.LOG_TAG, "Activity unbindService");
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.music_play);
        context=this;
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorIndego));
        initView();

    }

    private void initView() {


    }

    @Override
    public void update(Observable observable, Object o) {

        updateUI();
    }

    private void updateUI() {
        //PlayerState代表播放状态
        switch (mService.getPlayerState()) {
            case 1: {
                musicPlayImg.setImageResource(R.drawable.play);
                //Toast.makeText(context, "music is prepared", Toast.LENGTH_SHORT).show();
                break;
            }
            case 2: {
                musicPlayImg.setImageResource(R.drawable.pause);
                //Toast.makeText(context, "music is playing", Toast.LENGTH_SHORT).show();
                break;
            }
            case 3: {
                musicPlayImg.setImageResource(R.drawable.play);
                //Toast.makeText(context, "music is paused", Toast.LENGTH_SHORT).show();
                break;

            }

        }
        CurrentMusicNameTx.setText(mService.currentMusic.getMusicName());
        playStatusBar.setMax((int) (mService.currentMusic.duration/1000));
        Log.w("TAG","TotalProgress-----------"+mService.currentMusic.duration/1000);
        
        artWorkImg.setImageBitmap(BitmapFactory.decodeFile(mService.currentMusic.getAlbumUri()));
       new Thread(new Runnable() {
           @Override
           public void run() {
               while(mService.getPlayerState()==mService.MediaPlayer_PLAY){
                   Message msg=Message.obtain();
                   int progress=mService.getPlayerInstance().getCurrentPosition()/1000;
                   msg.obj=progress;
                   Log.w("TAG","progress-----------"+progress);
                   msg.what=0;
                   handler.sendMessage(msg);
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }

           }
       }).start();
/*
        MusicInfo currentMusic = mService.currentMusic;
        int position = mService.getMyList().indexOf(currentMusic);
        MusicInfo previous = new MusicInfo(), next = new MusicInfo();
        if (position == mService.getMyList().size() - 1) {
            previous = mService.getMyList().get(position - 1);
            next = mService.getMyList().get(0);
        }
        if (position == 0) {
            previous = mService.getMyList().get(mService.getMyList().size() - 1);
            next = mService.getMyList().get(1);
        }
        if (position != 0 && position != mService.getMyList().size() - 1) {
            previous = mService.getMyList().get(mService.getMyList().size() - 1);
            next = mService.getMyList().get(position + 1);
        }

        AlbumDetailFragment currentFragment = new AlbumDetailFragment();
        AlbumDetailFragment previousFragment = new AlbumDetailFragment();
        AlbumDetailFragment nextFragment = new AlbumDetailFragment();
        Bundle curBundle = new Bundle();
        curBundle.putParcelable("MusicInfo", currentMusic);
        Bundle prevBundle = new Bundle();
        prevBundle.putParcelable("MusicInfo", previous);
        Bundle nextBundle = new Bundle();
        nextBundle.putParcelable("MusicInfo", next);
        currentFragment.setArguments(curBundle);
        previousFragment.setArguments(prevBundle);
        nextFragment.setArguments(nextBundle);
        fragmentArrayList.add(0, previousFragment);
        fragmentArrayList.add(1, currentFragment);
        fragmentArrayList.add(2, nextFragment);
        FragmentManager manager = getSupportFragmentManager();
        pagerAdapter = new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return fragmentArrayList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentArrayList.size();
            }
        };
        viewPager.setAdapter(pagerAdapter);*/
    }
}
