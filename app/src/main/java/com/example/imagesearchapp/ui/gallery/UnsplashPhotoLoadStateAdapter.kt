package com.example.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.databinding.FooterUnsplashPhotoLoadStateBinding

class UnsplashPhotoLoadStateAdapter(private val onRetryButtonClick: () -> Unit) :
    LoadStateAdapter<UnsplashPhotoLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FooterUnsplashPhotoLoadStateBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(private val binding: FooterUnsplashPhotoLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                onRetryButtonClick.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}