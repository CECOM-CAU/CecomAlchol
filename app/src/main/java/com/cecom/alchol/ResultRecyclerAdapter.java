package com.cecom.alchol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ItemViewHolder> {
    DBHelper dbHelper;
    SQLiteDatabase db;

    private ArrayList<ResultData> mData = new ArrayList<>();

    String sql;
    final static String dbName = "favoriteTable.db";
    final static int dbVersion = 2;

    @NonNull
    @Override
    public ResultRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_result_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.tvMenu.setText(mData.get(position).getMenu());
        holder.tvSource.setText(mData.get(position).getSource());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String selMenu = mData.get(position).getMenu();

                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Add " + selMenu + " to Favorites?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper = new DBHelper(v.getContext(), dbName, null, dbVersion);
                                db = dbHelper.getWritableDatabase();
                                sql = "INSERT INTO favoriteTable VALUES('" + mData.get(position).getMenu() + "');";
                                db.execSQL(sql);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    void addItem(ResultData data) {
        mData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tvMenu;
        TextView tvSource;

        ItemViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvMenu = itemView.findViewById(R.id.menuName);
            tvSource = itemView.findViewById(R.id.menuSource);
        }
    }

    static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE favoriteTable (name TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS favoriteTable");
            onCreate(db);
        }

    }
}