package com.example.potatopaloozac.codingchallengeproj.ui;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.potatopaloozac.codingchallengeproj.R;

/**
 * IntentService that plays music in the background for 60 seconds
 */
public class MusicIntentService extends IntentService {

    private MediaPlayer mediaPlayer;

    public MusicIntentService() {
        super("MusicThread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        intent.setAction("music");
        try {
            mediaPlayer = MediaPlayer.create(MusicIntentService.this, R.raw.magic_to_do);
            mediaPlayer.start();
            Thread.sleep(60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra(
                "broadcastMessage", "Music has ended playing after 60 seconds..."));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
