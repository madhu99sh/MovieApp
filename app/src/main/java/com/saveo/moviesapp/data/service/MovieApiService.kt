package com.saveo.moviesapp.data.service

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movieList.php")
    suspend fun getMoviesList(
        @Query("pageIndex") pageIndex: Int,
        @Query("resultsperpage") pageSize: Int,
    ):  Response<JsonObject>
}