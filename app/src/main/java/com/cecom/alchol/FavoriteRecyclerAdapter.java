package com.cecom.alchol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {
    private ArrayList<String> mData = null ;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView) ;
            tvName = itemView.findViewById(R.id.favoriteName) ;
        }
    }

    FavoriteRecyclerAdapter(ArrayList<String> list) {
        mData = list ;
    }

    @Override
    public FavoriteRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_favorite_item, parent, false) ;
        FavoriteRecyclerAdapter.ViewHolder vh = new FavoriteRecyclerAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(FavoriteRecyclerAdapter.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.tvName.setText(text) ;
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}