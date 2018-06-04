package com.veni.rssreaderapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import java.util.List;
import com.squareup.picasso.Picasso;
import android.content.Intent;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    List<Article> mArticles;
    Context mContext;

    public ArticlesAdapter(List<Article> articles, Context context){
        mArticles = articles;
        mContext = context;
    }

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticlesAdapter.ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.textView.setText(article.getTitle());
        Picasso.get().load(article.getPictureUrl()).into(holder.imageView);
        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext,DetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ArticleField.ARTICLE_INDEX,position);
            mContext.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final ImageView imageView;
        final TextView textView;
        final CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.picture);
            textView = itemView.findViewById(R.id.title);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
