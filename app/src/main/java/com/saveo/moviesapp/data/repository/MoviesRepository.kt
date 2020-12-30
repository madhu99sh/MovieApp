package com.saveo.moviesapp.data.repository

import com.google.gson.JsonObject
import com.saveo.moviesapp.data.helper.MovieApiHelper
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiHelper: MovieApiHelper,
) {
    suspend fun getMoviesList(pageIndex: Int): Response<JsonObject> {
        return apiHelper.getMoviesList(pageIndex)
    }
}