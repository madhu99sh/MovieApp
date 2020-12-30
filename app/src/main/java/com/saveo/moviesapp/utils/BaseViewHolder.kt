package com.saveo.moviesapp.utils

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.saveo.moviesapp.BR

class BaseViewHolder(private val mBinding: ViewDataBinding) : RecyclerView.ViewHolder(
    mBinding.root
) {
    fun bind(obj: Any?, listener: Any?, position: Int) {
        mBinding.setVariable(BR.obj, obj)
        mBinding.setVariable(BR.listener, listener)
        mBinding.setVariable(BR.position, position)
        mBinding.executePendingBindings()
    }
}