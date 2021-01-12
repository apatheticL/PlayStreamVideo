package com.nhatle.testmediaplayer;

import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;

public class PlaybackStateListener  implements Player.EventListener {
    String TAG = PlaybackStateListener.class.getSimpleName();
    @Override
    public void onPlaybackStateChanged(int playbackState) {
        String stateString;
        switch (playbackState) {
            case ExoPlayer.STATE_IDLE:
                stateString = "ExoPlayer.STATE_IDLE      -";
                break;
            case ExoPlayer.STATE_BUFFERING:
                stateString = "ExoPlayer.STATE_BUFFERING -";
                break;
            case ExoPlayer.STATE_READY:
                stateString = "ExoPlayer.STATE_READY     -";
                break;
            case ExoPlayer.STATE_ENDED:
                stateString = "ExoPlayer.STATE_ENDED     -";
                break;
            default:
                stateString = "UNKNOWN_STATE             -";
                break;
        }
        Log.d(TAG, "changed state to " + stateString);
    }
}