package com.saveo.moviesapp.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saveo.moviesapp.data.model.Movie
import com.saveo.moviesapp.data.repository.MoviesRepository
import com.saveo.moviesapp.ui.detail.MovieDetailActivity
import com.saveo.moviesapp.utils.NetworkHelper
import com.saveo.moviesapp.utils.ObservableViewModel
import com.saveo.moviesapp.utils.Resource
import kotlinx.coroutines.launch

class MovieListViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    private val networkHelper: NetworkHelper,
    private val gson: Gson
) : ObservableViewModel() {
    var mContext: Activity? = null
    fun init(context: Activity) {
        mContext = context
    }

    private var isQueryExhausted: Boolean =
        false //query is exhausted when : data is null or empty, data returned < EXPECTED Total Result

    private var isPerformingQuery: Boolean =
        false //is performing query, as long as In loading state, not( Error or success)
    var cancelRequest = false

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int>
        get() = _pageNumber


    private val _movies = MediatorLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>>
        get() = _movies

    var bannerList = ArrayList<Movie>()

    init {
        _pageNumber.value = 1
    }

    //for first page
    fun getList() {
        if (!isPerformingQuery) {
            isQueryExhausted = false
            isPerformingQuery = true
            executeRequest()
        }
    }

    //for next page
    fun getNextPage() {
        if (!isQueryExhausted && !isPerformingQuery) {
            _pageNumber.value = _pageNumber.value?.plus(1)
            executeRequest()
        }
    }

    private fun executeRequest() {
        val repositorySource = MediatorLiveData<Resource<List<Movie>>>()
        viewModelScope.launch {
            try {
                if (networkHelper.isNetworkConnected()) {
                    val movies = ArrayList<Movie>()
                    moviesRepository.getMoviesList(pageNumber.value!!).let {
                        if (it.isSuccessful) {
                            val type = object : TypeToken<Movie>() {}.type
                            for (i in 0..17) {
                                movies.add(
                                    gson.fromJson(
                                        it.body()?.asJsonObject?.get("$i").toString(),
                                        type
                                    )
                                )
                            }
                        }
                        if (pageNumber.value == 1) {
                            bannerList = movies
                        }
                        repositorySource.postValue(Resource.Success(movies))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        registerMediatorLiveData(repositorySource)
    }


    fun registerMediatorLiveData(repositorySource: LiveData<Resource<List<Movie>>>) {
        _movies.addSource(repositorySource) { resourceListMovie ->
            if (!cancelRequest) {
                if (resourceListMovie != null) {
                    _movies.value = resourceListMovie
                    if (resourceListMovie is Resource.Success || resourceListMovie is Resource.Error) {
                        unregisterMediatorLiveData(repositorySource)
                        resourceListMovie.data?.let {
                            //if data is null (when error or succes) recyclerview will be invisible, so the user cannot scroll to fetch the next page anyway
                            if (it.size < _pageNumber.value!! * 10) {
                                isQueryExhausted = true
                            }
                        }
                    }
                } else {
                    unregisterMediatorLiveData(repositorySource)
                }
            } else {
                unregisterMediatorLiveData(repositorySource)
            }

        }
    }

    //unregister when whole response is null or when response ==Success or Error
    private fun unregisterMediatorLiveData(repositorySource: LiveData<Resource<List<Movie>>>) {
        isPerformingQuery = false
        _movies.removeSource(repositorySource)
    }

    fun resetPageNumber() {
        _pageNumber.value = 1
    }

    fun cancelRequest() {
        cancelRequest = true
        _pageNumber.value = 1
    }

    fun getMoviesList(pageIndex: Int): MutableLiveData<Resource<*>> {
        val _result = MutableLiveData<Resource<*>>()

        viewModelScope.launch {
            _result.postValue(Resource.Loading(null))

            try {
                if (networkHelper.isNetworkConnected()) {
                    val movies1 = ArrayList<Movie>()
                    moviesRepository.getMoviesList(pageIndex).let {
                        if (it.isSuccessful) {
                            val type = object : TypeToken<Movie>() {}.type
                            for (i in 0..9) {
                                movies1.add(
                                    gson.fromJson(
                                        it.body()?.asJsonObject?.get("$i").toString(),
                                        type
                                    )
                                )
                            }
                            _result.postValue(Resource.Success(movies1))
                        } else {
                            _result.postValue(Resource.Error("Something went wrong!!", null))
                        }
                    }
                } else {
                    _result.postValue(Resource.Error("No Internet Connection!!", null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _result.postValue(Resource.Error("Something went wrong!!", null))
            }

        }
        return _result
    }

    fun onItemClick(movie: Movie, view: View) {
        val intent = Intent(mContext, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie as Parcelable)
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(mContext!!, view, "itemImage")
        mContext?.startActivity(intent, options.toBundle())
    }
}
