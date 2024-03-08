package com.example.norwayapplicationxml.peresentation.general

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.common.ClickListener
import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.databinding.FragmentGeneralNewsBinding
import com.example.norwayapplicationxml.domain.model.New
import com.example.norwayapplicationxml.peresentation.commons.NewsViewModel
import com.example.norwayapplicationxml.peresentation.commons.NewsLoadStateAdapter
import com.example.norwayapplicationxml.peresentation.commons.NewsRecycleViewPagingAdapter
import com.example.norwayapplicationxml.peresentation.postDetails.PostDetailsFragment
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private const val TAG = "GeneralNewsFragment"

class GeneralNewsFragment : Fragment(), ClickListener {
    lateinit var binding: FragmentGeneralNewsBinding
    private val viewModel: GeneralNewsViewModel by viewModels()
    lateinit var adapter: NewsRecycleViewPagingAdapter
    lateinit var banalAdapter: BanalRecycleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsRecycleViewPagingAdapter(this)
        banalAdapter = BanalRecycleAdapter(Glide.with(requireActivity()))

        binding.newsRecycle.adapter = adapter
        binding.banalRecycleView?.adapter = banalAdapter
        CarouselSnapHelper().attachToRecyclerView(binding.banalRecycleView)

        adapter.withLoadStateFooter(NewsLoadStateAdapter {
            adapter.retry()
        })
        adapter.addOnPagesUpdatedListener {
            binding.newsRecycle.isVisible = true
            binding.shimmerLayout.isVisible = false
            binding.loadingIndicator.isVisible = false
        }


        lifecycleScope.launch {
            viewModel.news.collectLatest {
                adapter.submitData(it)
            }
        }

        MainScope().launch {
            viewModel.newsBanal.collectLatest {
                when (it) {
                    is Resource.failed -> {
                        Log.d(TAG, "onViewCreated Error: ${it.messaage.message}")
                    }

                    is Resource.success -> {
                        binding.shimmerEffect?.isVisible = false
                        binding.banalRecycleView?.isVisible = true
                        Log.d(TAG, "onViewCreated: succeeded: ${it.data}")
                        banalAdapter.submitList(it.data)
                    }
                    else -> {
                        Log.d(TAG, "onViewCreated: initiated")
                    }
                }
            }
        }
        adapter.addLoadStateListener { loadState ->
            when {
                loadState.prepend is LoadState.Error -> {
                    val err = (loadState.prepend as LoadState.Error)
                    Log.d(TAG, "onViewCreated: error: ${err.error.message}")

                }

                loadState.append is LoadState.Error -> {
                    val err = (loadState.append as LoadState.Error)
                    Log.d(TAG, "onViewCreated: error: ${err.error.message}")
                }

                loadState.refresh is LoadState.Error -> {
                    val err = (loadState.refresh as LoadState.Error)
                    Log.d(TAG, "onViewCreated: error: ${err.error.message}")
                    ShowSnackBarError()
                }

                else -> {
                    null
                }
            }
        }
    }

    private fun ShowSnackBarError() {
        val snak =
            Snackbar.make(binding.root, "Network error click try again", Snackbar.LENGTH_INDEFINITE)
        snak.setAction("retry") {
            adapter.retry()
        }
        snak.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneralNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onClick(extras: FragmentNavigator.Extras, new: New) {
        try {
            findNavController().navigate(
                R.id.action_generalNewsFragment_to_postDetailsFragment,
                PostDetailsFragment.getInstance(new).requireArguments(),
                null,
                extras
            )
        } catch (e: Exception) {
            Log.d(TAG, "onClick: $e")
        }
    }
}