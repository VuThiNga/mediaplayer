package com.t3h.ngagerrard.musicplayerbysugi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Nga Gerrard on 17/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MediaViewHolder> implements View.OnClickListener {
    private IMediaAdapter iMediaAdapter;
    private SimpleDateFormat formatDuration = new SimpleDateFormat("mm:ss");
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

    public RecyclerViewAdapter(IMediaAdapter iMediaAdapter) {
        this.iMediaAdapter = iMediaAdapter;
    }


    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new MediaViewHolder(inflater.inflate(R.layout.custom_song, parent, false), this);
    }

    @Override
    public void onBindViewHolder(final MediaViewHolder holder, final int position) {
        MediaOffline mediaOffline = iMediaAdapter.getData(position);
//        Bitmap bitmap = BitmapFactory.decodeFile(mediaOffline.getAlbumArt());
//
//        holder.imgSong.setImageBitmap(bitmap);

        holder.imgSong.setImageResource(mediaOffline.getImg());
        holder.tvNameSong.setText(mediaOffline.getDisplayName());
        if(mediaOffline.getComposer().equals("")){
            holder.tvComposer.setText("unknow");
        }
        holder.tvComposer.setText(mediaOffline.getComposer());
        holder.tvTime.setText(formatDuration.format(mediaOffline.getDuration()));
        holder.iconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iMediaAdapter.OnClickItem(position, holder.iconLike);
            }
        });

    }


    @Override
    public int getItemCount() {
        return iMediaAdapter.getCount();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_song:
                IGetPosition position = (IGetPosition) view.getTag();
                iMediaAdapter.OnClickData(position.getPosition());
                break;
            default:
                break;
        }
    }

    static final class MediaViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSong;
        private TextView tvNameSong;
        private TextView tvComposer;
        private TextView tvTime;
        private ImageView iconLike;
        private LinearLayout layout;

        public MediaViewHolder(View itemView, View.OnClickListener click) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.img_song);
            tvNameSong = itemView.findViewById(R.id.tv_name_song);
            tvComposer = itemView.findViewById(R.id.tv_composer);
            tvTime = itemView.findViewById(R.id.tv_time);
            iconLike = itemView.findViewById(R.id.img_icon_like);
            layout = itemView.findViewById(R.id.layout_song);
            layout.setTag(new IGetPosition() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }
            });
            layout.setOnClickListener(click);
        }
    }

    public interface IMediaAdapter{
        int getCount();
        MediaOffline getData(int position);
        void OnClickItem(int position, View view);
        void OnClickData(int position);
    }
}
