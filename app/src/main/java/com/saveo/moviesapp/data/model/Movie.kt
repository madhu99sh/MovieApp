package com.saveo.moviesapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var movieid: Int?,
    var movieName: String?,
    var actor: String?,
    var releaseyear: String?,
    var description: String?,
    var duration: String?,
    var genre: String?,
    var director: String?,
    var imdbRating: String?,
    var imageUrl: String?,
    var imageUrl_200X280: String?,
): Parcelable {
    constructor() : this(null, null, null, null, null, null, null,null,null,null,null)
}