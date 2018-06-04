package com.veni.rssreaderapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ArticlesAdapter mAdapter;
    static List<Article> articles;
    Retrofit retrofit;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mRecyclerView = findViewById(R.id.feed_recycler);
        articles = new ArrayList<>();

        final String BASE_URL = "https://lifehacker.com/rss/";
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        mAdapter = new ArticlesAdapter(articles, getBaseContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
        getData();
    }

    private void getData(){

        mDisposable.add(Observable.just(retrofit.create(BackendAPI.class)).subscribeOn(Schedulers.computation())
                .flatMap(s -> {
                    Observable<Feed> rss1
                            = s.loadRssFromLifehacker().subscribeOn(Schedulers.io());

                    Observable<Feed> rss2
                            = s.loadRssFromFeedburner().subscribeOn(Schedulers.io());

                    return Observable.concat(rss1, rss2);
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults, this::handleError));
    }

    private void handleResults(Feed feed) {
        if(feed.getArticleList()!=null){
            articles.addAll(feed.getArticleList());
            mAdapter.notifyDataSetChanged();
        }
    }


    private void handleError(Throwable throwable) {
        Toast.makeText(getBaseContext(), "Some error happen",Toast.LENGTH_LONG);
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.clear();
        }
        super.onDestroy();

    }

}
