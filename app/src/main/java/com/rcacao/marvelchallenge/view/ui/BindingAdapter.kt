package com.rcacao.marvelchallenge.view.ui

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rcacao.marvelchallenge.GlideApp
import com.rcacao.marvelchallenge.domain.model.CharacterModel
import com.rcacao.marvelchallenge.view.ui.detail.OnImageLoadListener
import com.rcacao.marvelchallenge.view.viewmodel.CharactersViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel

@BindingAdapter("glideSrc")
fun imageRes(view: ImageView, url: String) {
    GlideApp.with(view.context)
        .load(url)
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
        .listener(requestListener)
        .into(view)

}

@BindingAdapter("onSelectChar", "character", "onClickItem", "itemPosition")
fun setCallbacks(
    view: View,
    sharedViewModel: SharedViewModel,
    character: CharacterModel,
    charactersViewModel: CharactersViewModel,
    itemPosition: Int
) {
    view.setOnClickListener {
        sharedViewModel.selectCharacter(character)
        charactersViewModel.savePosition(itemPosition)
    }

}
