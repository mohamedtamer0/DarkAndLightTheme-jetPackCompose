package com.example.darkandlighttheme_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.darkandlighttheme_jetpackcompose.ui.theme.DarkAndLightThemejetPackComposeTheme
import com.example.darkandlighttheme_jetpackcompose.utils.AppTheme
import com.example.darkandlighttheme_jetpackcompose.utils.ThemeSetting
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeStetting: ThemeSetting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val theme = themeStetting.themeStream.collectAsState()
            val userDarkColors = when (theme.value) {
                AppTheme.MODE_AUTO -> isSystemInDarkTheme()
                AppTheme.MODE_DAY -> false
                AppTheme.MODE_NIGHT -> true
            }
            DarkAndLightThemejetPackComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ComposeThemeScreen(
                        onItemSelected = { theme -> themeStetting.theme = theme }
                    )
                }
            }
        }
    }
}

@Composable
fun ComposeThemeScreen(
    onItemSelected: (AppTheme) -> Unit
) {

}


