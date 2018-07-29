package com.t3h.ngagerrard.musicplayerbysugi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Nga Gerrard on 14/03/2018.
 */

public class SongFragment extends Fragment {
    private RecyclerView rvSong;
    private RecyclerViewAdapter adapter;
    private MediaManager mediaManager;

    ITranferFragmentToActivity inter;

    public SongFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageSong = inflater.inflate(R.layout.fragment_song, container, false);
        initView(pageSong);
        return pageSong;
    }

    private void initView(final View view) {
        mediaManager = new MediaManager(getActivity());
        mediaManager.getAllListAudio();
        rvSong = view.findViewById(R.id.rcv_song_list);

        rvSong.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(new RecyclerViewAdapter.IMediaAdapter() {
            @Override
            public int getCount() {
                if(mediaManager.getAudioOfflines() == null){
                    return 0;
                }
                return mediaManager.getAudioOfflines().size();
            }

            @Override
            public MediaOffline getData(int position) {
                return mediaManager.getAudioOfflines().get(position);
            }

            @Override
            public void OnClickItem(int position, View view1) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view1);
                popupMenu.inflate(R.menu.menu_setting_song);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.add_favorite_song:
                                break;
                            case R.id.detail:
                                break;
                            case R.id.delete:
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

            @Override
            public void OnClickData(int position) {
                //lay duoc position, chuyen position qua activity
                inter.tranferData(position);
            }
        });

        rvSong.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inter = (ITranferFragmentToActivity) getActivity();
    }

    public interface ITranferFragmentToActivity{
        void tranferData(int position);
    }
}
