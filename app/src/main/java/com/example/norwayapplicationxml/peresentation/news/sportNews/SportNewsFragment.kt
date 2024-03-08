package com.example.norwayapplicationxml.peresentation.news.sportNews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.common.ClickListener
import com.example.norwayapplicationxml.databinding.FragmentSportNewsBinding
import com.example.norwayapplicationxml.domain.model.New
import com.example.norwayapplicationxml.peresentation.commons.NewsLoadStateAdapter
import com.example.norwayapplicationxml.peresentation.commons.NewsRecycleViewPagingAdapter
import com.example.norwayapplicationxml.peresentation.commons.NewsViewModel
import com.example.norwayapplicationxml.peresentation.postDetails.PostDetailsFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "SportNews"
class SportNewsFragment : Fragment() , ClickListener{
    lateinit var binding: FragmentSportNewsBinding
    private val viewModel : SportNewsViewModel by viewModels()
    lateinit var adapter: NewsRecycleViewPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsRecycleViewPagingAdapter(this)
        binding.newsRecycle.adapter = adapter

        adapter.withLoadStateFooter(NewsLoadStateAdapter{
            adapter.retry()
        })
        adapter.addOnPagesUpdatedListener {
            binding.newsRecycle.isVisible = true
            binding.shimmerLayout.isVisible = false
            binding.loadingIndicator.isVisible  = false
        }
        lifecycleScope.launch {
            viewModel.news.collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener {loadState->
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
        val snak = Snackbar.make(binding.root , "Network error click try again" , Snackbar.LENGTH_INDEFINITE)
        snak.setAction("retry"){
            adapter.retry()
        }
        snak.show()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportNewsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onClick(extras: FragmentNavigator.Extras, new: New) {
        try{
            findNavController().navigate(R.id.action_sportNews_to_postDetailsFragment
                , PostDetailsFragment.getInstance(new).requireArguments() , null , extras)
        }catch (e:Exception){
            Log.d(TAG, "onClick: $e")
        }
    }
}