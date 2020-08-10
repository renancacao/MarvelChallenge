package com.rcacao.marvelchallenge.view.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rcacao.marvelchallenge.GlideApp
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.view.ui.details.OnImageLoadListener

@BindingAdapter("isFavorite")
fun isFavorite(view: ImageView, isFavorite: Boolean) {
    view.setImageDrawable(
        view.context.getDrawable(
            if (isFavorite) {
                R.drawable.ic_baseline_star_24
            } else {
                R.drawable.ic_baseline_star_border_24
            }
        )
    )
}

@BindingAdapter("glideSrc")
fun imageRes(view: ImageView, url: String) {
    GlideApp.with(view.context)
        .load(url)
        .fitCenter()
        .error(R.drawable.ic_baseline_broken_image_24)
        .into(view)
}

@BindingAdapter("glideSrc", "onImageLoad")
fun imageRes(view: ImageView, url: String, listener: OnImageLoadListener) {
    val requestListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
        ): Boolean {
            listener.onImageLoad()
            return false
        }

        override fun onResourceReady(
            res: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            listener.onImageLoad()
            return false
        }
    }
    GlideApp.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_broken_image_24)
        .error(R.drawable.ic_baseline_broken_image_24)
        .fitCenter()
        .listener(requestListener)
        .into(view)

}
