package com.t3h.ngagerrard.musicplayerbysugi;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.SystemClock;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * Created by Nga Gerrard on 17/03/2018.
 */

public class ManagerMediaPlayer implements MediaPlayer.OnCompletionListener {
    private MediaPlayer mediaPlayer;
    private static final String TAG = ManagerMediaPlayer.class.getSimpleName();
    private updateUI mUpdateUI;
    private AsynTaskAudio asynTaskAudio;
    public ManagerMediaPlayer() {

    }

    public ManagerMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;

    }

    public ManagerMediaPlayer(MediaPlayer mediaPlayer, AsynTaskAudio asynTaskAudio, updateUI mUpdateUI) {
        this.mediaPlayer = mediaPlayer;
        this.asynTaskAudio = asynTaskAudio;
        this.mUpdateUI = mUpdateUI;
    }
    public ManagerMediaPlayer(updateUI mUpdateUI) {
        this.mUpdateUI = mUpdateUI;
    }

    public void setDataSource(String path)
            throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepare();
        mediaPlayer.setOnCompletionListener(this);
        //mediaPlayer.prepare();
        mUpdateUI.updateTotalDuration(mediaPlayer.getDuration());
        if(asynTaskAudio != null){
            asynTaskAudio.isRunning= false;
            asynTaskAudio.cancel(true);
        }
        asynTaskAudio = new AsynTaskAudio();
        asynTaskAudio.isRunning = true;
        asynTaskAudio.executeOnExecutor(Executors.newFixedThreadPool(1));
    }

    public void play() {
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void release() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.release();
        mediaPlayer = null;
    }
    public void seek(long currentPercent){
        if(mediaPlayer != null){
            mediaPlayer.seekTo((int) (currentPercent * mediaPlayer.getDuration()/100));
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if(asynTaskAudio != null){
            asynTaskAudio.isRunning = false;
            asynTaskAudio.cancel(false);
            mUpdateUI.completeMusic();
        }
    }

    private class AsynTaskAudio extends AsyncTask<Void, Float, Void>{
        private boolean isRunning;

        @Override
        protected Void doInBackground(Void... voids) {
            long totalDuration = mediaPlayer.getDuration();
            while (isRunning){
                if(mediaPlayer != null){
                    long current = mediaPlayer.getCurrentPosition();
                    float percent = current * 100/totalDuration;
                    publishProgress(percent);
                }
                SystemClock.sleep(500);
            }
            return null;
        }

        //update giao dien
        //chuyen data tu class con sang class cha, su dung interface.
        @Override
        protected void onProgressUpdate(Float... values) {
            if(mUpdateUI != null){
                mUpdateUI.updateCurrentPercentPlay(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public interface updateUI{
        void updateTotalDuration(long duration);
        void updateCurrentPercentPlay(float duration );
        void completeMusic();
    }


}
