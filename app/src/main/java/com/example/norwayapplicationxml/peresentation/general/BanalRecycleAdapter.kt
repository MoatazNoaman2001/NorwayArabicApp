package com.example.norwayapplicationxml.peresentation.general

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.databinding.BanalListItemBinding
import com.example.norwayapplicationxml.domain.model.New
import com.example.norwayapplicationxml.peresentation.postDetails.PostDetailsFragment

class BanalRecycleAdapter(val imageLoader : RequestManager):  ListAdapter<New , BanalRecycleAdapter.ViewHolder>(Df()) {
    class ViewHolder(val binding: BanalListItemBinding,val imageLoader: RequestManager) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(item : New){
            imageLoader.load(item.image).into(binding.carouselImageView)
            binding.title.text= item.title

            binding.root.setOnClickListener {
                try {
                    Navigation.findNavController(it).navigate(R.id.action_generalNewsFragment_to_postDetailsFragment
                    , PostDetailsFragment.getInstance(item).requireArguments())
                }catch (e:Exception){

                }
            }
        }
    }
    class Df : DiffUtil.ItemCallback<New>(){
        override fun areItemsTheSame(oldItem: New, newItem: New): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: New, newItem: New): Boolean {
            return true
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(BanalListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false) , imageLoader)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val new = getItem(position)
        if (new != null)
            holder.bindItem(new)
    }
}