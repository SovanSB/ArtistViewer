package com.yandex.mobile_school.artistviewer.art;

import android.content.ContentValues;
import android.net.Uri;

import com.yandex.mobile_school.artistviewer.BuildConfig;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sovan on 23.04.2016.
 */
public class ArtistItem {
    public static final Uri URI = Uri.parse("content://" + BuildConfig.APPLICATION_ID + "/artists");

    private long id;
    private String name;
    private List<String> genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private CoverItem cover;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres == null ? Collections.<String>emptyList() : genres;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public CoverItem getCover() {
        return cover == null ? new CoverItem() : cover;
    }

    public static interface Columns {
        String ARTIST_ID = "artist_id";
        String NAME = "name";
        String GENRES = "genres";
        String TRACKS = "tracks";
        String ALBUMS = "albums";
        String LINK = "link";
        String DESCRIPTION = "description";
        String SMALL = "small";
        String BIG = "big";
    }

    public ContentValues toValues(){
        final ContentValues values = new ContentValues();
        values.put(Columns.ARTIST_ID, id);
        values.put(Columns.NAME, name);
        values.put(Columns.GENRES, genresString());
        values.put(Columns.TRACKS, tracks);
        values.put(Columns.ALBUMS, albums);
        values.put(Columns.LINK, link);
        values.put(Columns.DESCRIPTION, description);
        values.put(Columns.SMALL, cover.getSmall());
        values.put(Columns.BIG, cover.getBig());
        return values;
    }

    public String genresString() {
        StringBuilder sb = new StringBuilder();
        int genreNumber = genres.size();
        int counter = 0;
        for (String genre : genres) {
            counter++;
            sb.append(genre);
            if (counter < genreNumber) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
