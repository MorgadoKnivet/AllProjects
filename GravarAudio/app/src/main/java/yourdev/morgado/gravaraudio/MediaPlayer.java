package yourdev.morgado.gravaraudio;

import android.content.Context;

public class MediaPlayer {

    android.media.MediaPlayer mp;
    int music = R.raw.toque_hotline_bling;

    public android.media.MediaPlayer playMusic(Context context, android.media.MediaPlayer mp){
        try {
            if (mp.isPlaying()) {
                mp.release();
                mp = null;
                return mp;
            } else {

                mp = android.media.MediaPlayer.create(context, R.raw.toque_hotline_bling);
                mp.start();
                return mp;
            }
        }catch (NullPointerException n){
            mp = android.media.MediaPlayer.create(context, R.raw.toque_hotline_bling);
            mp.start();
            return mp;
        }

    }

    public android.media.MediaPlayer pauseMusic(android.media.MediaPlayer mp){
        try {
            mp.reset();
            if (!mp.isPlaying()) {
                mp.stop();
            }
            mp.reset();
            return mp;
        }catch (NullPointerException n){
            return mp;
        }
    }

}
