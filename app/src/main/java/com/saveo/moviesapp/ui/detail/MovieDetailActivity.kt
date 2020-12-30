package com.saveo.moviesapp.ui.detail

import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.saveo.moviesapp.BaseApp
import com.saveo.moviesapp.R
import com.saveo.moviesapp.data.model.Movie

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        if (intent.extras != null) {
            val movie = intent.extras?.getParcelable<Parcelable>("movie") as Movie
            findViewById<TextView>(R.id.tvMovieName).text = movie.movieName
            findViewById<TextView>(R.id.tvMovieDesc).text = movie.description
            val options = RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_broken_image)
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(true)
                .priority(Priority.HIGH)

            Glide.with(this)
                .load(
                    BaseApp.mInstance?.getGlideUrl(movie.imageUrl_200X280)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(findViewById(R.id.movie_image))
        }
    }
}