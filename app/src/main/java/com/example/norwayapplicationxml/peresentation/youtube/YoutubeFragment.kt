package com.example.norwayapplicationxml.peresentation.youtube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.norwayapplicationxml.common.Constants.extractYTId
import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.databinding.FragmentYoutubeBinding
import com.example.norwayapplicationxml.databinding.YoutubeItemRecycleViewBinding
import com.example.norwayapplicationxml.domain.model.YoutubeVideo
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "YoutubeFragment"
@AndroidEntryPoint
class YoutubeFragment : Fragment() , ClickListener {
    lateinit var binding:FragmentYoutubeBinding
    private val viewModel : YoutubeViewModelImpl by viewModels()
    lateinit var adapter : YoutubeListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(binding.youtubePlayerView)

        adapter = YoutubeListAdapter(Glide.with(requireContext()),  this)
        binding.recycleView.adapter = adapter
        lifecycleScope.launch {
            viewModel.getYoutubeList().collectLatest {
                when (it) {
                    is Resource.failed -> {
                        val snack = Snackbar.make(requireView() , "Error has been happened" , Snackbar.LENGTH_SHORT).setAction("ok"){

                        }
                        snack.show()
                    }
                    is Resource.success -> {
                        Log.d(TAG, "onViewCreated: youtube List: ${it.data}")
                        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                    youTubePlayer.loadVideo(extractYTId(it.data[0].link)!!, 0F)
                            }
                        })


                        binding.shimmerList.isVisible = false
                        binding.recycleView.isVisible = true
                        adapter.submitList(it.data)
                    }
                    null -> {

                    }
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYoutubeBinding.inflate(layoutInflater)
        return binding.root
    }
    class DG : DiffUtil.ItemCallback<YoutubeVideo>(){
        override fun areItemsTheSame(oldItem: YoutubeVideo, newItem: YoutubeVideo): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: YoutubeVideo, newItem: YoutubeVideo): Boolean {
            return true
        }

    }


    class YoutubeListAdapter(val imageLoader : RequestManager , val clickListener: ClickListener) : ListAdapter<YoutubeVideo , YoutubeListAdapter.ViewHolder>(DG()){
        inner class ViewHolder(
            val binding: YoutubeItemRecycleViewBinding,
            val imageLoader: RequestManager
        ) : RecyclerView.ViewHolder(binding.root){
            fun bindItem(item : YoutubeVideo){
                binding.title.text = item.title
                binding.duration.text = item.duration
                imageLoader.load(item.thumbnail).centerCrop().into(binding.image)
                binding.rootLayout.setOnClickListener {
                    clickListener.onClick(item.link)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(YoutubeItemRecycleViewBinding.inflate(LayoutInflater.from(parent.context) , parent , false) , imageLoader)

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val video = getItem(position)
            if (video != null)
                holder.bindItem(video)
        }
    }

    override fun onClick(link: String) {
        binding.youtubePlayerView.initialize(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(extractYTId(link)!! , 0f)
            }
        })
    }
}