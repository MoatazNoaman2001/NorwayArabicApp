package com.example.norwayapplicationxml.peresentation.startApp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.databinding.ActivityStartBinding
import com.example.norwayapplicationxml.peresentation.commons.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "StartActivity"

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStartBinding
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        installSplashScreen().setKeepOnScreenCondition {isLoading}
//
//
//        viewModel.getAppLanguage {
//            Log.d(TAG, "onCreate: language: $it")
//            if (it.isNullOrEmpty()){
//                isLoading = !isLoading
//            }else{
//                LanguageHelper.changeLanguage(this@StartActivity , it)
//                startActivity(Intent(this@StartActivity , MainActivity::class.java))
//                finish()
//            }
//        }

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_start)
        appBarConfiguration = AppBarConfiguration(navController.graph)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_start)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}