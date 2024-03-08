package com.example.norwayapplicationxml.peresentation.platforms

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.databinding.SocialRecycleItemBinding
import com.example.norwayapplicationxml.domain.model.WebPair


class PlatFromRecycleViewAdapter(val imageLoader: RequestManager) : ListAdapter<WebPair , PlatFromRecycleViewAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<WebPair>(){
        override fun areItemsTheSame(oldItem: WebPair, newItem: WebPair): Boolean {
            return oldItem.title  == newItem.title
        }

        override fun areContentsTheSame(oldItem: WebPair, newItem: WebPair): Boolean {
            return true
        }
    }
) {
    class ViewHolder(val binding: SocialRecycleItemBinding, val imageLoader: RequestManager) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: WebPair) {
            binding.socialMediaName.text = item.title
            binding.root.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                binding.root.context.startActivity(browserIntent)
            }
            when(item.title){
                "Facebook" -> setIcon(R.drawable.facebook)
                "Twitter" -> { setIcon(R.drawable.twitter);binding.socialMediaName.text = "X" }
                "Youtube" -> setIcon(R.drawable.youtube)
                "Instagram" -> setIcon(R.drawable.instagram)
                "Soundcloud" -> setIcon(R.drawable.sound_cloud)
                "Flickr" -> setIcon(R.drawable.flickr)
                "Tiktok" -> setIcon(R.drawable.tiktok)
            }
        }

        private fun setIcon(drawable: Int) {
            imageLoader.load(drawable)
                .into(binding.SocialMediaIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SocialRecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false) , imageLoader)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            holder.bindItem(item)
    }
}