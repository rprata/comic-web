package com.rprata.comicapp.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rprata.comicapp.R;
import com.rprata.comicapp.core.Comic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rprata on 07/01/17.
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.MyViewHolder> {

    private List<Comic> comicList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView picture;


        public MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            title = (TextView) view.findViewById(R.id.title);
            picture = (ImageView) view.findViewById(R.id.picture);
        }
    }


    public ComicAdapter(List<Comic> comicList) {
        this.comicList = comicList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comic, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comic comic = comicList.get(position);

        holder.title.setText(comic.getTitle());
        Picasso.with(mContext)
                .load(comic.getPicture_url())
                .resize(70, 70)
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }
}

