package com.yandex.mobile_school.artistviewer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yandex.mobile_school.artistviewer.R;
import com.yandex.mobile_school.artistviewer.art.ArtistItem;

/**
 * Created by Sovan on 23.04.2016.
 */
public class ArtistAdapter extends ResourceCursorAdapter {
    public ArtistAdapter(Context context, Cursor c) {
        super(context, R.layout.li_item, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final String name = cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.NAME));
        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(name);
        final String genres = cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.GENRES));
        TextView textViewGenres = (TextView) view.findViewById(R.id.textViewGenres);
        textViewGenres.setText(genres);
        int albums = cursor.getInt(cursor.getColumnIndex(ArtistItem.Columns.ALBUMS));
        int tracks = cursor.getInt(cursor.getColumnIndex(ArtistItem.Columns.TRACKS));
        final String info = albumEnding(albums) + " • " + trackEnding(tracks);
        TextView textViewAlbums = (TextView) view.findViewById(R.id.textViewAlbums);
        textViewAlbums.setText(info);
        final String url = cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.SMALL));

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if (TextUtils.isEmpty(url))
        {
            imageView.setVisibility(View.GONE);
        }
        else {
            imageView.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(url)
                    .fit()
                    .centerCrop()
                            //   .resize(200,200)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        }
    }

    String trackEnding(int num) {
        if (num < 0) {
            return "песен нет, ещё должен оказался!";
        }
        if (num == 0) {
            return "известных песен нет";
        }
        int counter = num % 100; // After hundred-like interval everything is repeated

        if (counter >= 5 && counter < 21) {
            return num + " песен";
        }
        counter %= 10;

        if (counter == 1) {
            return num + " песня";
        }
        if (counter > 1 && counter < 5) {
            return num + " песни";
        }

        return num + " песен";
    }

    String albumEnding(int num) {
        if (num < 0) {
            return "албомов нет, ещё должен оказался!";
        }
        if (num == 0) {
            return "известных альбомов нет";
        }
        int counter = num % 100; // After hundred-like interval everything is repeated

        if (counter >= 5 && counter < 21) {
            return num + " альбомов";
        }
        counter %= 10;

        if (counter == 1) {
            return num + " альбом";
        }
        if (counter > 1 && counter < 5) {
            return num + " альбома";
        }

        return num + " альбомов";
    }
}
