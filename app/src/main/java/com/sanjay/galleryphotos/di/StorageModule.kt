package com.sanjay.galleryphotos.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    @Singleton
    fun customSharedPref( @ApplicationContext context: Context?) = context?.getSharedPreferences("mySharedPref", Context.MODE_PRIVATE)


    private inline fun SharedPreferences.edit(perform: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        editor.apply {
            perform.invoke(editor)
            apply()
        }
    }

    /**
     * put data in shared preferences
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

//    fun getString(key: String?, defValue: Int): Int {
////         return   customSharedPref(context).getInt(key, defValue)
//    }
}