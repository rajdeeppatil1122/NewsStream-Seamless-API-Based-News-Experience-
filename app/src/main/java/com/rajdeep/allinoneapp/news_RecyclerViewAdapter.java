package com.rajdeep.allinoneapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class news_RecyclerViewAdapter extends RecyclerView.Adapter<news_RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<news_ModelClass> arrayList;

    int lastPosition = -1;      // For animation...

    public news_RecyclerViewAdapter(Context context, ArrayList<news_ModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public news_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_recycler_view_items, null, false);
        return new ViewHolder(view);

        /*EXPANDED FORM OF INFLATION --->

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
        * */
    }

    @Override
    public void onBindViewHolder(@NonNull news_RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, news_WebView.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("url", arrayList.get(holder.getAdapterPosition()).getUrl());
                intent.putExtras(mBundle);
//                intent.putExtra("url", arrayList.get(holder.getAdapterPosition()).getUrl());
//                Log.d("URL RECYCLER ADAPTER ", arrayList.get(holder.getAdapterPosition()).getUrl());
                context.startActivity(intent);
            }
        });


        holder.mPublishedAt.setText("Published At: " + arrayList.get(position).getPublishedAt());
        holder.mAuthorName.setText("Author Name: " + arrayList.get(position).getAuthor());
        holder.mHeadline.setText(arrayList.get(position).getTitle());
        holder.mContent.setText(arrayList.get(position).getDescription());

        Glide.with(context).load(arrayList.get(position).getUrlToImage()).into(holder.mNewsImage);


        setAnimation(context, holder.itemView, position);     // Setting animation on a specific view...
        // We have to set the setAnimation method at the end because we first have to set the view and then we have to animate it which will look good.


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void setAnimation(Context context, View viewToAnimate, int position) {

        if(position>lastPosition) {
            Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.custom_recyclerview_animation);
            viewToAnimate.startAnimation(slideIn);
            lastPosition = position;
        }

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHeadline, mContent, mPublishedAt, mAuthorName, mUrl;
        ImageView mNewsImage;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadline = itemView.findViewById(R.id.newsItemMainHeading);
            mContent = itemView.findViewById(R.id.newsItemContent);
            mPublishedAt = itemView.findViewById(R.id.newsItemPublishedTime);
            mAuthorName = itemView.findViewById(R.id.newsItemAuthorName);
            mNewsImage = itemView.findViewById(R.id.newsItemImage);
            cardView = itemView.findViewById(R.id.newsItemCardView);
        }
    }
}
