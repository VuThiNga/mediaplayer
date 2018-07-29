package com.t3h.ngagerrard.musicplayerbysugi;

import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SongFragment.ITranferFragmentToActivity, ManagerMediaPlayer.updateUI, SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tvSongPlay;
    ImageView imgStop, imgSongImage, imgRepeat, imgShuffle, imgPlay, imgPrevious, imgNext;
    MediaManager mediaManager;
    private ManagerMediaPlayer managerMediaplayer;
    TextView tvTime, tvDuration;
    int position = 0;
    List<MediaOffline> listMedia;
    Long totalDuration;
    SeekBar seekBar;

    int color[] = {Color.BLUE, Color.CYAN, Color.RED, Color.YELLOW};
    SimpleDateFormat formatDuration = new SimpleDateFormat("mm:ss");
    //Handler handlerColor;
    LinearLayout layoutColor;
    private boolean isTouching;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("BÀI HÁT"));
        tabLayout.addTab(tabLayout.newTab().setText("ALBUM"));
        tabLayout.addTab(tabLayout.newTab().setText("NGHỆ SĨ"));
        isTouching = false;
        //tabLayout.addTab(tabLayout.newTab().setText("ƯU THÍCH"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setAdapter(pagerAdapter);
        init();
    }

    private void init() {
        tvSongPlay = findViewById(R.id.tv_playing_song_name);
        imgStop = findViewById(R.id.img_stop);
        imgSongImage = findViewById(R.id.img_song_player);
        imgRepeat = findViewById(R.id.img_repeat);
        imgPrevious = findViewById(R.id.img_previous);
        imgPlay = findViewById(R.id.img_play);
        imgNext = findViewById(R.id.img_next);
        imgShuffle = findViewById(R.id.img_shuffle);
        imgPlay.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgStop.setOnClickListener(this);
        seekBar = findViewById(R.id.seek_bar);
        tvTime = findViewById(R.id.tv_time_music);
        tvDuration = findViewById(R.id.tv_duration);


        mediaManager = new MediaManager(this);
        mediaManager.getAllListAudio();
        managerMediaplayer = new ManagerMediaPlayer(this);

        listMedia = mediaManager.getAudioOfflines();
        layoutColor = (LinearLayout)findViewById(R.id.layout_color);
    }


    public void displayMusic(int position){
        MediaOffline mediaOffline = listMedia.get(position);
        tvSongPlay.setText(mediaOffline.getDisplayName());
        imgSongImage.setImageResource(mediaOffline.getImg());
        layoutColor.setBackgroundColor(color[position % 4]);
        try {
            managerMediaplayer.setDataSource(mediaOffline.getPath());
            managerMediaplayer.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //tvTime.setText(formatDuration.format(mediaOffline.getDuration()));

    }
    @Override
    public void tranferData(int position) {
        this.position = position;
        displayMusic(position);
    }

    @Override
    protected void onDestroy() {
        managerMediaplayer.release();
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouching = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int percent = seekBar.getProgress();
        long current = percent * totalDuration /100;
        managerMediaplayer.seek(current);
        isTouching = false;
    }


    @Override
    public void updateTotalDuration(long duration) {
        totalDuration = duration;
        tvTime.setText(formatDuration.format(totalDuration));
    }

    @Override
    public void updateCurrentPercentPlay(float duration) {
        if(isTouching){
            return;
        }
        seekBar.setProgress((int)duration);
        tvDuration.setText(formatDuration.format((long)duration * totalDuration/100));
    }

    @Override
    public void completeMusic() {
        position++;
        if(position == listMedia.size()){
            position = 0;
        }
        displayMusic(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_repeat:

                break;
            case R.id.img_next:
                position++;
                if(position > listMedia.size() - 1) {
                    position = 0;
                }
                displayMusic(position);
                break;
            case R.id.img_previous:
                position--;
                if(position < 0){
                    position = listMedia.size() - 1;
                }

                displayMusic(position);
                break;
            case R.id.img_play:

            case R.id.img_stop:
                if(managerMediaplayer.isPlaying()){
                    imgStop.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.play_anim);
                    imgPlay.startAnimation(animation);
                    imgPlay.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                    managerMediaplayer.pause();
                }else {
                    imgStop.setImageResource(R.drawable.ic_pause_black_24dp);
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.play_anim);
                    imgPlay.startAnimation(animation);
                    imgPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                    managerMediaplayer.play();
                }
                break;
        }
    }
}
