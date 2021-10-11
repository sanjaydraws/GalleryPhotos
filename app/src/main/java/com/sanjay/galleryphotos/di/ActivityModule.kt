package com.sanjay.galleryphotos.di

import android.content.Context
import com.sanjay.galleryphotos.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Named


@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

//    @Singleton only worked with applicationComponent(singletoncomponent)

    @ActivityScoped // it won't create another string over and over again
    @Provides
    @Named("String3")
    fun provideTestString3(
        @ApplicationContext context: Context?,
        @Named("String1")testString1:String
    ) = "${context?.getString(R.string.app_name)} - $testString1"

}

