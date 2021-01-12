package com.nhatle.testmediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback {

        private static final String TAG = "MediaPlayerDemo";
        private int mVideoWidth;
        private int mVideoHeight;
        private MediaPlayer mMediaPlayer;
        private SurfaceView mPreview;
        private SurfaceHolder holder;
//        private String path= "https://file.mentor.vn/files/books/lesson/output/file-1596611538411/playlist.m3u8";
        private String path= "https://file.mentor.vn/files/books/lesson/file-1596611538411.mp4";


        private boolean mIsVideoSizeKnown = false;
        private boolean mIsVideoReadyToBePlayed = false;

        /**
         *
         * Called when the activity is first created.
         */
        @Override
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            setContentView(R.layout.activity_main);
            mPreview = (SurfaceView) findViewById(R.id.surface);
            holder = mPreview.getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        }

        private void playVideo(String url) {
            doCleanUp();
                try {
                    // Create a new media player and set the listeners
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(url);
                    mMediaPlayer.setDisplay(holder);
                    mMediaPlayer.prepare();
                    mMediaPlayer.setOnBufferingUpdateListener(this);
                    mMediaPlayer.setOnCompletionListener(this);
                    mMediaPlayer.setOnPreparedListener(this);
                    mMediaPlayer.setOnVideoSizeChangedListener(this);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                }catch (Exception e){
                    e.printStackTrace();
                }

        }

        public void onBufferingUpdate(MediaPlayer arg0, int percent) {
            Log.d(TAG, "onBufferingUpdate percent:" + percent);

        }

        public void onCompletion(MediaPlayer arg0) {
            Log.d(TAG, "onCompletion called");
        }

        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            Log.v(TAG, "onVideoSizeChanged called");
            if (width == 0 || height == 0) {
                Log.e(TAG, "invalid video width(" + width + ") or height(" + height
                        + ")");
                return;
            }
            mIsVideoSizeKnown = true;
            mVideoWidth = width;
            mVideoHeight = height;
            if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
                startVideoPlayback();
            }
        }

        public void onPrepared(MediaPlayer mediaplayer) {
            Log.d(TAG, "onPrepared called");
            mIsVideoReadyToBePlayed = true;
            if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
                startVideoPlayback();
            }
        }

        public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
            Log.d(TAG, "surfaceChanged called");

        }

        public void surfaceDestroyed(SurfaceHolder surfaceholder) {
            Log.d(TAG, "surfaceDestroyed called");
        }

        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated called");
            playVideo(path);

        }

        @Override
        protected void onPause() {
            super.onPause();
            releaseMediaPlayer();
            doCleanUp();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            releaseMediaPlayer();
            doCleanUp();
        }

        private void releaseMediaPlayer() {
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }

        private void doCleanUp() {
            mVideoWidth = 0;
            mVideoHeight = 0;
            mIsVideoReadyToBePlayed = false;
            mIsVideoSizeKnown = false;
        }

        private void startVideoPlayback() {
            Log.v(TAG, "startVideoPlayback");
            holder.setFixedSize(mVideoWidth, mVideoHeight);
            mMediaPlayer.start();
        }
}