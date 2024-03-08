package com.example.norwayapplicationxml.peresentation.platforms

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.norwayapplicationxml.databinding.FragmentPlatFromsBinding
import com.example.norwayapplicationxml.domain.model.WebPair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

private const val TAG = "PlatFormsFragment"
class PlatFormsFragment : Fragment() {
    lateinit var binding: FragmentPlatFromsBinding
    lateinit var adapter: PlatFromRecycleViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlatFromRecycleViewAdapter(Glide.with(requireContext()))
        binding.platFromRecycleView.adapter = adapter
        lifecycleScope.launch {
            val platforms = getSocialListItems()
            binding.platFromRecycleView.isVisible = true
            binding.shimmerLayout?.isVisible = false
            adapter.submitList(platforms)
        }
    }

    suspend fun getSocialListItems():  List<WebPair>{
        val result = withContext(Dispatchers.IO) {
            val items = ArrayList<WebPair>()
            val dc = Jsoup.connect("https://norwayvoice.no/").get()
            val socialList = dc.getElementsByClass("elementor-social-icons-wrapper elementor-grid").select("span").toList()
            for (element in socialList) {
                val content = element.select("a")
                val link = content.attr("href")
                val text = element.getElementsByClass("elementor-screen-only").text()
                val socialItem = WebPair(link, text)
                if (!link.isNullOrEmpty())
                    items.add(socialItem)
            }

            items
        }
        return result
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlatFromsBinding.inflate(layoutInflater)
        return binding.root
    }
}