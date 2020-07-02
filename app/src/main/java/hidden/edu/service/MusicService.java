package hidden.edu.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicService extends IntentService {
    public static String FILE_PATH;
    MediaPlayer mediaPlayer = null;

    String file;

    /*
     * @param name
     * @deprecated
     */
    public MusicService() {
        super("MusicService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        file = intent.getStringExtra(FILE_PATH);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer(); // initialize it here
        try {
            mediaPlayer.setDataSource(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);
        // prepare async to not block main thread
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnErrorListener((mp, what, extra) -> false);
    }


    @Override
    public void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}

