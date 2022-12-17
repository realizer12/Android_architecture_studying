package com.example.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


//리사이클러뷰에
@BindingAdapter("bind:addList")
fun <T, VH : RecyclerView.ViewHolder> RecyclerView.addList(list: List<T>?) {
    (adapter as ListAdapter<T, VH>).submitList(list?.toMutableList())
}