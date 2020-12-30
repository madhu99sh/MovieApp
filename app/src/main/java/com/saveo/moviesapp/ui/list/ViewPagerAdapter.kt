package com.saveo.moviesapp.ui.list

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.saveo.moviesapp.BaseApp
import com.saveo.moviesapp.R
import com.saveo.moviesapp.utils.BaseAdapter
import com.saveo.moviesapp.utils.BaseViewHolder

class ViewPagerAdapter(
    private val context: Context,
    private val mLayoutId: Int,
    private val mViewModel: MovieListViewModel
) : BaseAdapter() {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val options = RequestOptions()
            .fitCenter()
            .placeholder(R.drawable.ic_broken_image)
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(true)
            .priority(Priority.HIGH)
        Glide.with(context)
            .load(
                BaseApp.mInstance?.getGlideUrl(mViewModel.bannerList[position].imageUrl_200X280)
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(options)
            .into(holder.itemView.findViewById(R.id.banner))
    }


    override fun getLayoutIdForPosition(position: Int): Int {
        return mLayoutId
    }

    override fun getObjForPosition(position: Int): Any {
        return mViewModel.bannerList[position]
    }

    override fun getListenerForPosition(position: Int): Any {
        return mViewModel
    }

    override fun getItemCount(): Int {
        return if (mViewModel.bannerList.size > 0) {
            mViewModel.bannerList.size
        } else 0
    }

}