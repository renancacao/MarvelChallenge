package com.rcacao.marvelchallenge.view.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.rcacao.marvelchallenge.GlideApp

@BindingAdapter("glideSrc")
fun imageRes(view: ImageView, url: String) {
    GlideApp.with(view.context)
        .load(url)
        .into(view)
}
