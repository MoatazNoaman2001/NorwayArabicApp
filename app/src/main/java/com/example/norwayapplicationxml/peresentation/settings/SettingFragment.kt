package com.example.norwayapplicationxml.peresentation.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.databinding.FragmentSettingBinding
import com.example.norwayapplicationxml.peresentation.commons.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


private const val TAG = "SettingFragment"
@AndroidEntryPoint
class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    private val settingViewModel :SettingsFragmentViewModel by viewModels()
    private lateinit var supportedLanguageList :Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportedLanguageList = arrayOf(
            getString(R.string.english),
            getString(R.string.arabic),
            getString(R.string.norsk),
        )
        settingViewModel.getAppLanguage {
            Log.d(TAG, "onViewCreated: getting language inside setting fragment : $it")
            MainScope().launch {
            when (it) {
                "ar" -> {
                    binding.autoCompleteMenu.setText(getString(R.string.arabic))
                }
                "en" -> {
                    binding.autoCompleteMenu.setText(getString(R.string.english))
                }
                "no" -> {
                    binding.autoCompleteMenu.setText(getString(R.string.norsk))
                }
                else -> {

                }
            }

            }
        }

        settingViewModel.getTheme {
            when (it) {
                "0" -> {
                    binding.DarkThemeCheckbox.isChecked = false
                }
                "1" -> {
                    binding.DarkThemeCheckbox.isChecked = true
                }
                else -> {}
            }
        }
        binding.apply {
            DarkThemeCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    settingViewModel.setTheme(1)
                else
                    settingViewModel.setTheme(0)
            }


            val arrAdapter = ArrayAdapter(requireContext() , android.R.layout.simple_list_item_1, android.R.id.text1,
                supportedLanguageList
            )
//            autoCompleteMenu.setAdapter(arrAdapter)
            binding.LangInputText.setEndIconOnClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_languageSelectFragment)
            }
//            autoCompleteMenu.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//                Log.d(TAG, "onViewCreated: $position")
//                when (binding.autoCompleteMenu.text.toString()) {
//                    getString(R.string.english) -> {
//                        settingViewModel.selectLanguage("en")
//                    }
//                    getString(R.string.arabic) -> {
//                        settingViewModel.selectLanguage("ar")
//                    }
//                    getString(R.string.norsk) -> {
//                        settingViewModel.selectLanguage("no")
//                    }
//                    else -> {}
//                }
//
//            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }
}