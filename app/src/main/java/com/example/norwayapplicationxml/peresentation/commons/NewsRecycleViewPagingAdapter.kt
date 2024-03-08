package com.example.norwayapplicationxml.peresentation.commons

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.norwayapplicationxml.common.ClickListener
import com.example.norwayapplicationxml.databinding.NewsRecycleItemBinding
import com.example.norwayapplicationxml.domain.model.New
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG = "NewsRecycleViewPagingAd"
class NewsRecycleViewPagingAdapter(val clickListener: ClickListener) : PagingDataAdapter<New, NewsRecycleViewPagingAdapter.ViewHolder>(ItemRecycleDiffUtil()) {
    class ViewHolder(val binding: NewsRecycleItemBinding, val clickListener: ClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(new: New){
            binding.contentTextView.text = new.content
            binding.titleTextView.text = new.title
            binding.publishDay.text = convertIsoStringToDate(new.date)?.let {
                SimpleDateFormat("yyyy MMM dd").format(
                    it
                )
            }
            binding.publish.text = new.cats.joinToString { it.title }
            binding.publishedBy.text = new.by
            binding.readTime.text = new.readDuration
            binding.titleTextView.transitionName = "titleSmall_$position"
            binding.mainCard.transitionName = "cardSmall_$position"
            binding.mainCard.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.titleTextView to "titleBig",
                    binding.mainCard to "cardBig"
                )
                Log.d(TAG, "bindView: item clicked")
                clickListener.onClick(extras, new)
            }

            Glide.with(binding.root.context).load(new.image)
                .centerCrop()
                .into(binding.newImage)
        }
        fun convertIsoStringToDate(isoDateString: String): Date? {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            return try {
                format.parse(isoDateString)
            } catch (e: Exception) {
                null
            }
        }
    }



    class ItemRecycleDiffUtil : DiffUtil.ItemCallback<New>(){
        override fun areItemsTheSame(oldItem: New, newItem: New): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: New, newItem: New): Boolean {
            return true
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val new = getItem(position)
        if (new != null)
            holder.bindView(new)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsRecycleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickListener)
    }
}