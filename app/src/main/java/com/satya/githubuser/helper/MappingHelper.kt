package com.satya.githubuser.helper

import android.database.Cursor
import com.satya.githubuser.db.DatabaseContract
import com.satya.githubuser.entity.Favorite
import java.util.*

object MappingHelper {

    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<Favorite> {
        val favList = ArrayList<Favorite>()

        favCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.PHOTO))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.REPOSITORY))
                val followers = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING))
                val favorite = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FAVORITE))

                favList.add(
                    Favorite(
                        username,
                        name,
                        photo,
                        company,
                        location,
                        repository,
                        followers,
                        following,
                        favorite
                    )
                )
            }
        }
        return favList
    }
}