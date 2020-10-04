package com.miguelete.twitterpins.ui

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?){
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?:  View.GONE
}