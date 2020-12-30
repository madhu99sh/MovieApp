package com.saveo.moviesapp

import android.app.Application
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : Application() {

    private var mGlideHeader: LazyHeaders? = null

    companion object {
        var mInstance: BaseApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
//        BaseApp.registerForNetworkChangeEvents(this)
    }

//    fun registerForNetworkChangeEvents(context: Context) {
//        val networkStateChangeReceiver = NetworkStateChangeReceiver()
//        context.registerReceiver(
//            networkStateChangeReceiver,
//            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//        )
//        context.registerReceiver(
//            networkStateChangeReceiver,
//            IntentFilter(BaseApp.WIFI_STATE_CHANGE_ACTION)
//        )
//    }

    fun getGlideUrl(url: String?): GlideUrl? {
        if (mGlideHeader != null) {
            return GlideUrl(url, mGlideHeader)
        }
        val builder = LazyHeaders.Builder()
        val lazyHeaders: LazyHeaders
            lazyHeaders = builder.addHeader("x-device-platform", "android")
                .addHeader("x-rapidapi-key", "773912f8b3mshc1ca5d483adcef7p1e0bb9jsnc55e6e671efa")
                .addHeader("x-rapidapi-host", "devru-bigflix-movies-download-v1.p.rapidapi.com")
                .build()
            mGlideHeader = lazyHeaders
        return GlideUrl(url, lazyHeaders)
    }
}