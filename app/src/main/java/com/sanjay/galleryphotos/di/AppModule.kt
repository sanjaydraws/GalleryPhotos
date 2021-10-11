package com.sanjay.galleryphotos.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) // this dependency provide as long as application will live
object AppModule {

    // have central place for dependency

    @Singleton // not necessary
    @Provides  // to prvide dependency
    @Named("String1") // for multiple dependencie at same time
    fun provideTestString1()=  "we will inject String"

    @Singleton // not necessary
    @Provides  // to prvide dependency
    @Named("String2")
    fun provideTestString2()=  "we will inject another String"

}