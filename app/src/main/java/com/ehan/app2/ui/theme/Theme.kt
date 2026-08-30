package com.ehan.app2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
// import androidx.compose.runtime.SideEffect
// import androidx.compose.ui.graphics.toArgb
// import androidx.compose.ui.platform.LocalContext
// import androidx.compose.ui.platform.LocalView
// import androidx.core.view.WindowCompat

@Composable
fun App2Theme(
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    //val context = LocalContext.current

    MaterialTheme(
       // colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}