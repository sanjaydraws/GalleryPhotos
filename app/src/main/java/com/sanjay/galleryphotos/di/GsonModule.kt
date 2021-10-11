package com.sanjay.galleryphotos.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object GsonModule{

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}