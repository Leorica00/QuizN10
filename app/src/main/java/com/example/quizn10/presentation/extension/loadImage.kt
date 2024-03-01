package com.example.quizn10.presentation.extension

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}