package com.example.norwayapplicationxml.peresentation.splashVideo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.databinding.FragmentStartSplashBinding
import com.example.norwayapplicationxml.peresentation.mainAppEntry.MainActivity
import com.example.norwayapplicationxml.peresentation.commons.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "StartSplashFragment"
@AndroidEntryPoint
class StartSplashFragment : Fragment() {
    lateinit var binding: FragmentStartSplashBinding
    lateinit var mediaController: android.widget.MediaController
    private val viewmodel: MainActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaController = android.widget.MediaController(requireContext())
//        binding.splashVideo.setMediaController(mediaController)
        val videoUri =
            Uri.parse("android.resource://${requireActivity().packageName}/${R.raw.splash_video}")
        binding.splashVideo.setVideoURI(videoUri)

        binding.splashVideo.setOnPreparedListener {
//            it.isLooping = true
            it.start()
        }

        binding.splashVideo.setOnCompletionListener {
            viewmodel.getAppLanguage {
                Log.d(TAG, "onViewCreated: $it")
                if (it == null)
                    findNavController().navigate(R.id.action_startSplashFragment_to_selectLanguageFragment)
                else{
                    requireActivity().startActivity(Intent(requireContext() , MainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartSplashBinding.inflate(layoutInflater)
        return binding.root
    }
}