package com.example.norwayapplicationxml.peresentation.startApp.selectLanguage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.norwayapplicationxml.peresentation.mainAppEntry.MainActivity
import com.example.norwayapplicationxml.databinding.FragmentSelectLanguageBinding
import com.example.norwayapplicationxml.peresentation.commons.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectLanguageFragment : Fragment() {
    lateinit var binding: FragmentSelectLanguageBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            EnglishLanguage.setOnClickListener {
                viewModel.selectLanguage("en")
                requireActivity().startActivity(Intent(requireContext() , MainActivity::class.java))
            }

            norwayLanguage.setOnClickListener {
                viewModel.selectLanguage("no")
                requireActivity().startActivity(Intent(requireContext() , MainActivity::class.java))
            }

            ArabicLanguage.setOnClickListener {
                viewModel.selectLanguage("ar")
                requireActivity().startActivity(Intent(requireContext() , MainActivity::class.java))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectLanguageBinding.inflate(layoutInflater)
        return binding.root
    }
}