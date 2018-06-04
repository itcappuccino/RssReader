package com.veni.rssreaderapp;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BackendAPI {
    @GET("https://lifehacker.com/rss/")
    Observable<Feed> loadRssFromLifehacker();

    @GET("http://feeds.feedburner.com/TechCrunch/")
    Observable<Feed> loadRssFromFeedburner();
}
