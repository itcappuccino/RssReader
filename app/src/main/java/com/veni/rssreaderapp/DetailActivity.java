package com.veni.rssreaderapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ImageView image;
    Button openBtn;
    String imageUrl, articleUrl;
    TextView pubDate, title;
    Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        int index = getIntent().getIntExtra(ArticleField.ARTICLE_INDEX, -1);
        article = MainActivity.articles.get(index);
        imageUrl = article.getPictureUrl();
        articleUrl = article.getLink();
        image = findViewById(R.id.picture);
        openBtn = findViewById(R.id.open);
        pubDate = findViewById(R.id.pubDate);
        title = findViewById(R.id.title);
        loadImage(imageUrl);
        openBtn.setOnClickListener(v -> openLink(articleUrl));
        title.setText(article.getTitle());
        pubDate.setText(article.getPubDate().toString());


    }
    private void loadImage(String imageUrl){
        Picasso.get().load(imageUrl).into(image);
    }

    private void openLink(String link){
        Uri page = Uri.parse(link);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, page);
        startActivity(webIntent);
    }

}
