package com.fighter.animatedthemeswitch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fighter.animatedthemeswitch.ui.screens.MoonTheme
import com.fighter.animatedthemeswitch.ui.theme.AnimatedThemeSwitchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimatedThemeSwitchTheme {
                MoonTheme()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimatedThemeSwitchTheme {
        MoonTheme()
    }
}