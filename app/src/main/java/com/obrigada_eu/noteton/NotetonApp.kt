package com.obrigada_eu.noteton

import android.app.Application
import com.obrigada_eu.noteton.core.CacheManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NotetonApp : Application() {

    @Inject lateinit var cacheManager: CacheManager

    override fun onCreate() {
        super.onCreate()
        cacheManager.clearTempPhoto()
    }
}