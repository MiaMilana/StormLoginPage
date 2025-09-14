package ru.startandroid.stormloginpage.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = backgroundView
)

private val LightColorScheme = lightColorScheme(
    background = backgroundView
)

@Composable
fun StormLoginPageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun textFieldColors(isError: Boolean = false) = TextFieldDefaults.colors(
    unfocusedContainerColor = Color.Black,
    unfocusedTextColor = Color.Gray,
    unfocusedLabelColor = Color.DarkGray,
    unfocusedSupportingTextColor = Color.Red,
    focusedContainerColor = Color.Black,
    focusedIndicatorColor = if (!isError) Color.DarkGray else Color.Red,
    focusedLabelColor = Color.DarkGray,
    focusedSupportingTextColor = Color.Red
)