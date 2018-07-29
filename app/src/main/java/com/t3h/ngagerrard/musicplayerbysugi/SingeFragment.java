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

public class SingeFragment extends Fragment{
    public SingeFragment() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageSinger = inflater.inflate(R.layout.fragment_singer, container, false);
        return pageSinger;
    }
}
