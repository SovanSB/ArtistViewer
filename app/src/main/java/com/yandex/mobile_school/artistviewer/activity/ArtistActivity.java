package com.yandex.mobile_school.artistviewer.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yandex.mobile_school.artistviewer.R;
import com.yandex.mobile_school.artistviewer.adapter.ArtistAdapter;
import com.yandex.mobile_school.artistviewer.art.ArtistItem;
import com.yandex.mobile_school.artistviewer.views.ObservableScrollView;

/**
 * Created by Sovan on 24.04.2016.
 */
public class ArtistActivity extends AppCompatActivity implements ObservableScrollView.OnScrollListener{

    private ImageView mImageView;
    private ObservableScrollView mScrollView;
    private String mLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.support_artist_layout);
        Intent intent = getIntent();
        long artist_id = intent.getLongExtra(ArtistItem.Columns.ARTIST_ID, Long.MIN_VALUE);

        if (artist_id != Long.MIN_VALUE) {
            Cursor cursor = getContentResolver().query(ArtistItem.URI, null,
                    ArtistItem.Columns.ARTIST_ID + "=?", new String[]{Long.toString(artist_id)}, null);
            cursor.moveToFirst();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarArt);
            setSupportActionBar(toolbar);
            String name = "←  " + cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.NAME));
            this.setTitle(name);
            toolbar.setTitleTextColor(0xFFFFFFFF);



            int albums = cursor.getInt(cursor.getColumnIndex(ArtistItem.Columns.ALBUMS));
            int tracks = cursor.getInt(cursor.getColumnIndex(ArtistItem.Columns.TRACKS));
            ((TextView) findViewById(R.id.textViewAlbums2)).setText(ArtistAdapter.historyString(albums, tracks));
            String description = cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.NAME)) + ": " +
                    cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.DESCRIPTION));
            ((TextView) findViewById(R.id.textViewInfo)).setText(description);

            ((TextView) findViewById(R.id.textViewGenres2)).setText(
                    cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.GENRES))
            );
            mLink = cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.LINK));
            final String imageUrl = cursor.getString(cursor.getColumnIndex(ArtistItem.Columns.BIG));
            mImageView = (ImageView) findViewById(R.id.imageViewBig);
            if (!TextUtils.isEmpty(imageUrl)) {
                Picasso.with(this).load(imageUrl).fit().centerCrop().into(mImageView);
            }

            mScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScrollView.setOnScrollListener(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScrollView.setOnScrollListener(this);
    }

    @Override
    public void onScroll(int y) {
        mImageView.setTranslationY(y / 3);
    }

    public void onClick(View view) {
        if (!mLink.startsWith("http://") && !mLink.startsWith("https://")) {
            mLink = "http://" + mLink;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mLink));
        startActivity(browserIntent);
    }


    public void onLogoClick1(View view) {
        onBackPressed();
    }
}
