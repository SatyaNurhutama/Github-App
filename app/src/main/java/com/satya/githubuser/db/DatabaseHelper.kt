package com.satya.githubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.satya.githubuser.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "favoritedb"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAV = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FavoriteColumns.USERNAME} TEXT PRIMARY KEY  NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.PHOTO} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.COMPANY} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.REPOSITORY} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FOLLOWERS} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FOLLOWING} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAV)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}