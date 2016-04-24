package com.yandex.mobile_school.artistviewer.loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import com.yandex.mobile_school.artistviewer.api.ArtistListApi;
import com.yandex.mobile_school.artistviewer.art.ArtistItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Sovan on 23.04.2016.
 */
public class ArtistListLoader extends CursorLoader {
    final String mBaseUri;
    final String mEndPoint;

    public ArtistListLoader(Context context, String baseUri, String endPoint) {
        super(context, ArtistItem.URI, null, null, null, null);
        mBaseUri = baseUri;
        mEndPoint = endPoint;
    }

    @Override
    public Cursor loadInBackground() {

        Retrofit testRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUri)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        ArtistListApi service = testRetrofit.create(ArtistListApi.class);
        //   VersionItem testItem = null;
        Call<List<ArtistItem>> testCall = service.loadArtists(mEndPoint);
        List<ArtistItem> list;
        List<ContentValues> values = new ArrayList<>();
        try {
            Response<List<ArtistItem>> testResponse = testCall.execute();
            list = testResponse.body();
            for (ArtistItem pic : list) {
                values.add(pic.toValues());
            }
            final ContentValues[] bulkCategories = values.toArray(new ContentValues[values.size()]);
            ContentResolver db = getContext().getContentResolver();
            // Clearing base before refreshing
            db.delete(ArtistItem.URI, null, null);
            db.bulkInsert(ArtistItem.URI, bulkCategories);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.loadInBackground();
    }
}