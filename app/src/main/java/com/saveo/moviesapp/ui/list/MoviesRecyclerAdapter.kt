package com.saveo.moviesapp.ui.list

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.saveo.moviesapp.data.model.Movie
import com.saveo.moviesapp.databinding.MovieListItemBinding
import java.util.*

class MoviesRecyclerAdapter(
    private val viewModel: MovieListViewModel,
    val preloadSizeProvider: ViewPreloadSizeProvider<String>,
    val requestManager: RequestManager?
) :
    androidx.recyclerview.widget.ListAdapter<Movie, MoviesRecyclerAdapter.MovieViewHolder>(
        MovieDiffUtil()
    ), ListPreloader.PreloadModelProvider<String> {
    //preload of type string, because Glide caches the url

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.getInstance(parent, viewModel, preloadSizeProvider)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null)
            holder.bind(movie)

    }


    class MovieViewHolder private constructor(
        private val binding: MovieListItemBinding, val viewModel: MovieListViewModel,
        val preloadSizeProvider: ViewPreloadSizeProvider<String>
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(
                parent: ViewGroup,
                viewModel: MovieListViewModel,
                preloadSizeProvider: ViewPreloadSizeProvider<String>
            ): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MovieListItemBinding.inflate(inflater, parent, false)
                return MovieViewHolder(
                    binding,
                    viewModel, preloadSizeProvider
                )
            }
        }

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.viewHolder = viewModel
            preloadSizeProvider.setView(binding.movieImage)
            binding.executePendingBindings()
            binding.movieImage.setOnClickListener { view ->
                viewModel.onItemClick(
                    movie,
                    view
                )
            }
        }


    }

    class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.movieid == newItem.movieid

        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun getPreloadItems(position: Int): MutableList<String> {
        try {
            val url = getItem(position).imageUrl_200X280
            if (url == null || TextUtils.isEmpty(url)) {
                return Collections.emptyList()
            } else {
                return Collections.singletonList(url)
            }
        } catch (exception: Exception) {
            return Collections.emptyList()
        }

    }

    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*>? =
        requestManager?.load(item)
}