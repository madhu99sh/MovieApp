package com.saveo.moviesapp.utils

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

/**
 * Copyright (c) 2018 Truckish Labs OPC ltd.
 * Author : Madhusudan SH
 * Created At: 11-08-2018 14:26
 *
 *
 *
 *
 * Observable ViewModel which implements Observable and extends from ViewModel.
 * Every ViewModel which requires Observable, needs to be extended from this class.
 * It also needs to Override methods notifyChange and notifyPropertyChange based on the needs.
 */
open class ObservableViewModel : ViewModel(), Observable {
    private val mCallback = PropertyChangeRegistry()
    override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        mCallback.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        mCallback.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        mCallback.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        mCallback.notifyCallbacks(this, fieldId, null)
    }
}