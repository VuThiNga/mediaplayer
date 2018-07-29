package com.t3h.ngagerrard.musicplayerbysugi;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Nga Gerrard on 17/03/2018.
 */

public class MediaManager {
    private static final String TAG = MediaManager.class.getSimpleName();
    private Context context;
    private List<MediaOffline> audioOfflines =
            new ArrayList<>();

    public MediaManager(Context context) {
        this.context = context;
    }

    public void getAllListAudio() {
        //pa1: duong duong cua bang can query
        //pa2: la cac cot can lay, neu null thi lay het
        //par3 va pa4: menh de where
        //pare 5: kieu sap sep
        Cursor c =
                context.getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, null
                );
        if (c == null) {
            return;
        }
        //lay cac ten cot trong bang ra
        String[] columns = c.getColumnNames();
        for (String column : columns) {
            Log.d(TAG, "getAllListAudio column: " + column);
        }
        c.moveToFirst();
        int indexId = c.getColumnIndex("_id");
        int indexData = c.getColumnIndex("_data");
        int indexDisplayName = c.getColumnIndex("title");
        int indexMineType = c.getColumnIndex("mime_type");
        int indexDateAdd = c.getColumnIndex("date_added");
        int indexAlbumId = c.getColumnIndex("album_id");
        int indexDuration = c.getColumnIndex("duration");
        int indexComposer = c.getColumnIndex("artist");
        //int indexAlbumArt = c.getColumnIndex("track");

        while (!c.isAfterLast()){
            long id = c.getLong(indexId);
            String data = c.getString(indexData);
            String displayName = c.getString(indexDisplayName);
            String mineType = c.getString(indexMineType);
            long dateAdd = c.getLong(indexDateAdd); //giay
            long albumId = c.getLong(indexAlbumId);
            long duration = c.getLong(indexDuration);
            //String albumart = c.getString(indexAlbumArt);

            String composer = c.getString(indexComposer);
            MediaOffline audioOffline = new MediaOffline();
            audioOffline.setId(id);
            audioOffline.setPath(data);
            audioOffline.setDisplayName(displayName);
            audioOffline.setMineType(mineType);
            audioOffline.setDateCreated(
                    new Date(dateAdd*1000)
            );
            audioOffline.setAlbumId(albumId);
            audioOffline.setDuration(duration);
            audioOffline.setComposer(composer);
            List<Integer> img = new ArrayList<Integer>();
            img.add(R.drawable.bg);
            img.add(R.drawable.brick_bg);
            img.add(R.drawable.brick_bg);
            img.add(R.drawable.circle_ble);
            img.add(R.drawable.green_bg);
            img.add(R.drawable.party_bg);
            img.add(R.drawable.watercolor_bg);
            img.add(R.drawable.wooden_bg);
            Random r = new Random();
            int index = r.nextInt(7);
            audioOffline.setImg(img.get(index));
            Log.d("G", index + "");
            //audioOffline.setAlbumArt(albumart);
            //Log.d("SU", albumart);
            audioOfflines.add(audioOffline);
            c.moveToNext();
        }
        c.close();

    }

    public List<MediaOffline> getAudioOfflines() {
        return audioOfflines;
    }

    public void setAudioOfflines(List<MediaOffline> audioOfflines) {
        this.audioOfflines = audioOfflines;
    }
}
