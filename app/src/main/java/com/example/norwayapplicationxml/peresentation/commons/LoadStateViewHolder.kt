package com.example.norwayapplicationxml.peresentation.commons

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.databinding.LoadStateItemBinding
import com.google.android.material.progressindicator.CircularProgressIndicator

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.load_state_item, parent, false)
) {
    private val binding = LoadStateItemBinding.bind(itemView)
    private val progressBar: CircularProgressIndicator = binding.progressIndicator
    private val errorMsg: TextView = binding.errorMsg
    private val retry: Button = binding.retryBtn
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }

        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
    }
}