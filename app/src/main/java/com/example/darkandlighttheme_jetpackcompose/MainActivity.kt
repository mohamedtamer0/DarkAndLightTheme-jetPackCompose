package com.example.darkandlighttheme_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            DarkAndLightThemejetPackComposeTheme(darkTheme = userDarkColors) {
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
    val menuExpanded = remember { mutableStateOf(false) }


    Scaffold(topBar = {


        TopAppBar(title = {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Compose Theme")
            }


        },
            actions = {
                IconButton(onClick = { menuExpanded.value = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = null)
                }

                Column(modifier = Modifier.wrapContentSize(Alignment.TopCenter)) {
                    DropdownMenu(
                        expanded = menuExpanded.value,
                        onDismissRequest = { menuExpanded.value = false },
                        modifier = Modifier
                            .width(200.dp)
                            .wrapContentSize(
                                Alignment.TopStart
                            )
                    ) {


                        DropdownMenuItem(onClick = {
                            onItemSelected(AppTheme.fromOrdinal(AppTheme.MODE_AUTO.ordinal))
                            menuExpanded.value = false
                        })
                        {
                            Text(text = "Auto")
                        }


                        DropdownMenuItem(
                            onClick = {
                                onItemSelected(AppTheme.fromOrdinal(AppTheme.MODE_DAY.ordinal))
                                menuExpanded.value = false
                            }
                        ) {
                            Text(text = "Light Theme")
                        }
                        DropdownMenuItem(
                            onClick = {
                                onItemSelected(AppTheme.fromOrdinal(AppTheme.MODE_NIGHT.ordinal))
                                menuExpanded.value = false
                            }
                        ) {
                            Text(text = "Dark Theme")
                        }
                    }
                }

            }
        )
    },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.lorem_ipsum),
                    style = MaterialTheme.typography.body1,
                    fontSize = 18.sp
                )
            }
        }
    )
}


