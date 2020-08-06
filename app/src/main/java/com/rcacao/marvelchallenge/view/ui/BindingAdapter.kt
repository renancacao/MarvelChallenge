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
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.ui.details.OnImageLoadListener
import com.rcacao.marvelchallenge.view.ui.list.OnRecyclerItemClickListener
import com.rcacao.marvelchallenge.view.viewmodel.CharactersViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel

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
        .error(R.drawable.ic_baseline_broken_image_24)
        .fitCenter()
        .listener(requestListener)
        .into(view)

}

@BindingAdapter(
    "sharedViewModel",
    "character",
    "charactersViewModel",
    "itemPosition",
    "onItemClickListener"
)
fun setCallbacks(
    view: View,
    sharedViewModel: SharedViewModel,
    character: CharacterModel,
    charactersViewModel: CharactersViewModel,
    itemPosition: Int,
    onRecyclerItemClickListener: OnRecyclerItemClickListener
) {
    view.setOnClickListener {
        sharedViewModel.selectCharacter(character, it.transitionName)
        charactersViewModel.savePosition(itemPosition)
        onRecyclerItemClickListener.onClickItem(it)
    }

}
