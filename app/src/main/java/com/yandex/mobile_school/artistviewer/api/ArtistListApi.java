package com.yandex.mobile_school.artistviewer.api;

import com.yandex.mobile_school.artistviewer.art.ArtistItem;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Sovan on 23.04.2016.
 */
public interface ArtistListApi {
    @GET("{url}")
        //  @GET("https://dl.dropboxusercontent.com/s/gc7eluk8lgxodd7/filelist.txt?dl=0")
    Call<List<ArtistItem>> loadArtists(@Path("url") String url);
}