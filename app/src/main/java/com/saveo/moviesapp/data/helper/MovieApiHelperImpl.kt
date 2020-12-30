package com.saveo.moviesapp.data.helper

import com.google.gson.JsonObject
import com.saveo.moviesapp.data.service.MovieApiService
import retrofit2.Response
import javax.inject.Inject

class MovieApiHelperImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieApiHelper {

    override suspend fun getMoviesList(pageIndex: Int): Response<JsonObject> {
        return apiService.getMoviesList(pageIndex, 18)
    }
}