//package com.example.ebook_pnminh.SQLite;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//public class FavoritesDBHelper   extends SQLiteOpenHelper {
//    private static final String DATABASE_NAME = "favorites.db";
//    private static final int DATABASE_VERSION = 1;
//    public FavoritesDBHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_TABLE = "CREATE TABLE bookfavorites (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "email TEXT," +
//                "bookId INTEGER)";
//        db.execSQL(CREATE_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS bookfavorites");
//        onCreate(db);
//    }
//}
