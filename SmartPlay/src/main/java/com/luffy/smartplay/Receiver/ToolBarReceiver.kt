package com.luffy.smartplay.Receiver

import android.content.*
import com.luffy.smartplay.Config

/**
 * Created by axnshy on 16/7/31.
 */
class ToolBarReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //PlayerState代表播放状态
        if (intent.action === Config.Companion.MUSIC_UPDATE) {
            // PlayerState = intent.getIntExtra("PlayerState", 0x01);
            /*switch (PlayerService.PlayerState) {
                */
            /*
                * 1:CIRCLE
                * 2:PREVIOUS
                * 3:PLAY
                * 4:NEXT
                *
                *
                *
                * */
            /*
                case 1: {
                    mediaPlay.setImageResource(R.drawable.play);
                    //Toast.makeText(context, "music is prepared", Toast.LENGTH_SHORT).show();
                    TopMusicDisplay.setText("");
                    break;
                }
                case 2: {
                    mediaPlay.setImageResource(R.drawable.pause);
                    this.getTopMusicDisplay.setText("正在播放: " + PlayerService.currentMusic.getMusicName());
                    //Toast.makeText(context, "music is playing", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 3: {
                    mediaPlay.setImageResource(R.drawable.play);
                    TopMusicDisplay.setText(" ");
                    //Toast.makeText(context, "music is paused", Toast.LENGTH_SHORT).show();
                    break;

                }
            }*/
        }
    }
}