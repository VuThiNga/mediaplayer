package com.t3h.ngagerrard.musicplayerbysugi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Nga Gerrard on 14/03/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numberOfTabs;
    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                SongFragment songFragment = new SongFragment();
                return songFragment;
            case 1:
                AlbumFragment albumFragment = new AlbumFragment();
                return albumFragment;
            case 2:
                SingeFragment singeFragment = new SingeFragment();
                return singeFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
