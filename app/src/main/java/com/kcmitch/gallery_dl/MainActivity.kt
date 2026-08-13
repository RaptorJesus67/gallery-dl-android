package com.kcmitch.gallery_dl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kcmitch.gallery_dl.ui.GalleryDlViewModel
import com.kcmitch.gallery_dl.ui.screens.MainScreen
import com.kcmitch.gallery_dl.ui.screens.SettingsScreen
import com.kcmitch.gallery_dl.ui.theme.GalleryDlTheme

/**
 * Main Activity for gallery-dl Android application.
 * Kept strictly minimalist to prevent excess code in MainActivity.
 */
open class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GalleryDlAppContent()
        }
    }

    /**
     * Nested function wrapper to structure top-level compose initialization
     */
    @Composable
    private fun GalleryDlAppContent() {
        val viewModel: GalleryDlViewModel = viewModel()
        val settings by viewModel.settings.collectAsState()

        val isDarkTheme = when (settings.themeMode) {
            "dark" -> true
            "light" -> false
            else -> isSystemInDarkTheme()
        }

        GalleryDlTheme(
            darkTheme = isDarkTheme,
            themePreset = settings.themePreset
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "main",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("main") {
                    MainScreen(
                        viewModel = viewModel,
                        onNavigateToSettings = {
                            navController.navigate("settings")
                        }
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        viewModel = viewModel,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
