package com.saveo.moviesapp.ui.list


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.android.myapplication.movies.util.RecyclerViewDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.saveo.moviesapp.R
import com.saveo.moviesapp.data.model.Movie
import com.saveo.moviesapp.databinding.FragmentMovieListBinding
import com.saveo.moviesapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movies_list.*
import kotlinx.android.synthetic.main.fragment_movie_list.*


@AndroidEntryPoint
class MovieListFragment : Fragment() {
    val movieListViewModel: MovieListViewModel by viewModels()

    private lateinit var adapter: MoviesRecyclerAdapter
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        binding.viewModel = movieListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        movieListViewModel.init(requireActivity())
        initRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initRecyclerView() {

        movieListViewModel.getMoviesList(1).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    progressBar.visibility=View.GONE
                    movieListViewModel.bannerList = it.data as ArrayList<Movie>
                    val viewPagerAdapter =
                        ViewPagerAdapter(
                            requireContext(),
                            R.layout.viewpager_item,
                            movieListViewModel
                        )
                    viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                    viewpager.adapter = viewPagerAdapter
                    viewpager.offscreenPageLimit = 3
                    viewpager.currentItem=4

                    val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
                    val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

                    viewpager.setPageTransformer { page, position ->
                        val myOffset = position * -(2 * pageOffset + pageMargin)
                        if (viewpager.getOrientation() === ViewPager2.ORIENTATION_HORIZONTAL) {
                            if (ViewCompat.getLayoutDirection(viewpager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                                page.translationX = -myOffset
                            } else {
                                page.translationX = myOffset
                            }
                        } else {
                            page.translationY = myOffset
                        }
                    }
                }
                Resource.Status.ERROR -> {
                }

                Resource.Status.LOADING -> {
                }

            }
        })

        val viewPreloader = ViewPreloadSizeProvider<String>()
        binding.recyclerview.setNestedScrollingEnabled(false);
        binding.recyclerview.apply {
            this@MovieListFragment.adapter =
                MoviesRecyclerAdapter(
                    movieListViewModel, viewPreloader, initGlide()
                )
            addItemDecoration(RecyclerViewDecoration())
            adapter = this@MovieListFragment.adapter
        }
        val preloader = RecyclerViewPreloader(Glide.with(this), adapter, viewPreloader, 20)
        binding.recyclerview.addOnScrollListener(preloader)

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
            val view = binding.scrollView.getChildAt(binding.scrollView.childCount - 1) as View
            val diff: Int = view.bottom - (binding.scrollView.height + binding.scrollView
                .scrollY)
            if (diff == 0) {
                movieListViewModel.getNextPage()
            }
        })

    }

    private fun initGlide(): RequestManager? {
        val options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.ic_broken_image)
        return Glide.with(this).setDefaultRequestOptions(options)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMovieList()
    }

    //method to get the first page of result
    fun getMovieList() {
        movieListViewModel.resetPageNumber()
        movieListViewModel.getList()
    }
}


