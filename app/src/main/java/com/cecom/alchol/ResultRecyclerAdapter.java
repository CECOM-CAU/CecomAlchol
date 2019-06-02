package com.cecom.alchol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ItemViewHolder> {
    private ArrayList<ResultData> mData = new ArrayList<>();

    @NonNull
    @Override
    public ResultRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_result_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvMenu.setText(mData.get(position).getMenu());
        holder.tvSource.setText(mData.get(position).getSource());
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    void addItem(ResultData data) {
        mData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenu;
        TextView tvSource;

        ItemViewHolder(View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.menuName);
            tvSource = itemView.findViewById(R.id.menuSource);
        }
    }
}