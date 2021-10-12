package com.sanjay.galleryphotos.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sanjay.galleryphotos.R

/**
 * @param imageUrl pass image url
 * */

@BindingAdapter("loadImage")
fun ImageView.loadImage(imageUrl: String?){
    imageUrl?.let {
        Glide.with(this).load(imageUrl).
        centerCrop().
        placeholder(R.drawable.ic_launcher_background).
        into(this)
    }
}
