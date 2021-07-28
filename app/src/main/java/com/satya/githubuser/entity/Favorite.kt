package com.satya.githubuser.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var username: String? = null,
    var name: String? = null,
    var photo: String? = null,
    var company: String? = null,
    var location: String? = null,
    var repository: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var fav: String? = null
) : Parcelable