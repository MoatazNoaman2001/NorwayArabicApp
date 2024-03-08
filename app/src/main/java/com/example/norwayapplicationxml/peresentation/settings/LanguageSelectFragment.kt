package com.example.norwayapplicationxml.peresentation.settings

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.databinding.FragmentLanguageSelectListDialogItemBinding
import com.example.norwayapplicationxml.databinding.FragmentLanguageSelectListDialogBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "LanguageSelectFragment"

@AndroidEntryPoint
class LanguageSelectFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentLanguageSelectListDialogBinding? = null
    private val settingViewModel: SettingsFragmentViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLanguageSelectListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.layoutManager =
            LinearLayoutManager(context)
        binding.list.adapter = ItemAdapter(arrayOf(
            getString(R.string.english),
            getString(R.string.arabic),
            getString(R.string.norsk)
        ))
    }

    private inner class ViewHolder constructor(binding: FragmentLanguageSelectListDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val text: TextView = binding.text
    }

    private inner class ItemAdapter constructor(private val langs: Array<String>) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                FragmentLanguageSelectListDialogItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = langs[position]
            holder.text.setOnClickListener {
                Log.d(TAG, "onViewCreated: $position")
                when (langs[position]) {
                    getString(R.string.english) -> {
                        settingViewModel.selectLanguage("en")
                    }
                    getString(R.string.arabic) -> {
                        settingViewModel.selectLanguage("ar")
                    }
                    getString(R.string.norsk) -> {
                        settingViewModel.selectLanguage("no")
                    }
                    else -> {}
                }
            }
        }

        override fun getItemCount(): Int {
            return langs.size
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}