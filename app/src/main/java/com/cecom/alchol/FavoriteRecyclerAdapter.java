package com.cecom.alchol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {
    private ArrayList<String> mData;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private String dbName = "favoriteTable.db";
    private int dbVersion = 2;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        View mView;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.favoriteName);
            mView = itemView;
        }
    }

    FavoriteRecyclerAdapter(ArrayList<String> list) {
        mData = list;
    }

    @NonNull
    @Override
    public FavoriteRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_favorite_item, parent, false);
        return new FavoriteRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecyclerAdapter.ViewHolder holder, final int position) {
        String text = mData.get(position);
        holder.tvName.setText(text);
        if(!text.equals("조회결과가 없습니다.")){
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Select what you want.");
                    builder.setPositiveButton("Delete from Favorites",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                                    deleteDialog.setMessage("Delete this Favorite?");
                                    deleteDialog.setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialog, int which){
                                                    dbHelper = new FavoriteRecyclerAdapter.DBHelper(context, dbName, null, dbVersion);
                                                    db = dbHelper.getWritableDatabase();
                                                    db.delete("favoriteTable", "name = ?", new String[]{String.valueOf(mData.get(position))});
                                                    context.sendBroadcast(new Intent("UPDATE RECYCLER"));
                                                }
                                            });
                                    deleteDialog.setNegativeButton("No",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                    deleteDialog.show();
                                }
                            });
                    builder.setNegativeButton("View Recipe",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "View Recipe is not ready for now.", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class DBHelper extends SQLiteOpenHelper {
        DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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