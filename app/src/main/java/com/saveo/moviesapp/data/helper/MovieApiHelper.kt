package com.saveo.moviesapp.data.helper

import com.google.gson.JsonObject
import retrofit2.Response

interface MovieApiHelper {
    suspend fun getMoviesList(pageNumber: Int): Response<JsonObject>
}