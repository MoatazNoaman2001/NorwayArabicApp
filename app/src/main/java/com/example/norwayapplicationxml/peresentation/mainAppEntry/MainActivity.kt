package com.example.norwayapplicationxml.peresentation.mainAppEntry

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.common.ConnectionLiveData
import com.example.norwayapplicationxml.databinding.ActivityMainBinding
import com.example.norwayapplicationxml.domain.LanguageHelper
import com.example.norwayapplicationxml.peresentation.commons.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var controller: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        controller = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(controller.graph)
            .setOpenableLayout(binding.drawerLayout)
            .build()
        viewModel.getAppLanguage {
            Log.d(TAG, "onCreate lang: $it")

            LanguageHelper.changeLanguage(this@MainActivity, it!!)
            recreate()
        }

        ConnectionLiveData(this@MainActivity).observe(this) {
            binding.networkState.isVisible = !it
        }

        viewModel.getTheme {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    if (it?.toInt() != 1) {
                        Log.d(TAG, "onCreate: theme: change to light")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        recreate()
                    }
                }

                Configuration.UI_MODE_NIGHT_NO -> {
                    if (it?.toInt() != 0) {
                        Log.d(TAG, "onCreate: theme: change to night")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        recreate()
                    }
                }

                else -> {

                }
            }
        }

        NavigationUI.setupWithNavController(binding.toolbar, controller, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, controller)
        NavigationUI.setupWithNavController(binding.drawer, controller)

        controller.addOnDestinationChangedListener { controller, destination, arguments ->
            if (!arrayOf(R.id.generalNewsFragment, R.id.onBoardFragment).contains(destination.id)) {
                binding.bottomNavigationView.isVisible = false
                binding.bottomAppBar.isVisible = false
                binding.RadioButton.isVisible = false
            } else {
                binding.bottomNavigationView.isVisible = true
                binding.bottomAppBar.isVisible = true
                binding.RadioButton.isVisible = true
            }


//        findViewById<MaterialButton>(R.id.changeLangBtn).setOnClickListener {
//            viewModel.selectLanguage("")
//
//            viewModel.getAppLanguage {
//                if (it.isNullOrEmpty()){
//                    val ctx: Context = applicationContext
//                    val pm: PackageManager = ctx.packageManager
//                    val intent = pm.getLaunchIntentForPackage(ctx.packageName)
//                    val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
//                    ctx.startActivity(mainIntent)
//                    Runtime.getRuntime().exit(0)
//                }
//            }
//
//        }
        }


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val g = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val o = newConfig.orientation
        when (g) {
            Configuration.UI_MODE_NIGHT_YES -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                recreate()
//                LanguageHelper.changeLanguage(this@MainActivity , lang)
                Log.d(TAG, "onConfigurationChanged: night yes")
            }

            Configuration.UI_MODE_NIGHT_NO -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
//                LanguageHelper.changeLanguage(this@MainActivity , lang)
                Log.d(TAG, "onConfigurationChanged: night no")
            }
        }

        when (o) {
            Configuration.ORIENTATION_PORTRAIT -> {
//                LanguageHelper.changeLanguage(this@MainActivity , lang)

            }

            Configuration.ORIENTATION_LANDSCAPE -> {
//                LanguageHelper.changeLanguage(this@MainActivity , lang)

            }

            Configuration.ORIENTATION_SQUARE -> {
//                LanguageHelper.changeLanguage(this@MainActivity , lang)

            }

            Configuration.ORIENTATION_UNDEFINED -> {
//                LanguageHelper.changeLanguage(this@MainActivity , lang)

            }
        }
    }
}