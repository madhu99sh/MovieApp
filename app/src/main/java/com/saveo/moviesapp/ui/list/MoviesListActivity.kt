package com.saveo.moviesapp.ui.list

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.saveo.moviesapp.R
import com.saveo.moviesapp.data.model.Movie
import com.saveo.moviesapp.ui.detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movies_list.*


@AndroidEntryPoint
class MoviesListActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieListFragment())
                .commitNow()
        }

    }
}