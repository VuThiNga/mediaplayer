package com.t3h.ngagerrard.musicplayerbysugi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nga Gerrard on 14/03/2018.
 */

public class AlbumFragment extends Fragment {
    public AlbumFragment() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageAlbum = inflater.inflate(R.layout.fragment_album, container, false);
        return pageAlbum;
    }
}
