package com.saveo.moviesapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(
            layoutInflater, viewType, parent, false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        val obj = getObjForPosition(position)
        val listener = getListenerForPosition(position)
        holder.bind(obj, listener, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    protected abstract fun getObjForPosition(position: Int): Any
    protected abstract fun getListenerForPosition(position: Int): Any
    protected abstract fun getLayoutIdForPosition(position: Int): Int
}