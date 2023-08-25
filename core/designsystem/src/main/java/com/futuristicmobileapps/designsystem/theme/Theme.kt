package com.futuristicmobileapps.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView

private val LightColorScheme = darkColorScheme(
    background = Color(0xFFf9fafe),
    onPrimary = Color(0xFF34393a),
    inversePrimary = Color(0xFFfafcfe),
    primary = Color(0xFFe18138),
    primaryContainer = Color(0xFFffdbc7),
    secondary = Color(0xFFa0a2a8),
    onSecondary = Color(0xFF7e8080),
    secondaryContainer = Color(0xFFffede4),
    onPrimaryContainer = Color(0xFFffffff)
)

private val DarkColorScheme = lightColorScheme(
    background = Color(0xFF2b2c32),
    onPrimary = Color(0xFFfafcfe),
    onSecondary = Color(0xFFfafcfe).copy(alpha = 0.8f),
    inversePrimary = Color(0xFF34393a),
    primary = Color(0xFFFFEDE4),
    secondary = Color(0xFFac8a76),
    secondaryContainer = Color(0xFF964908),
    onPrimaryContainer = Color(0xFF353840),
)

@Composable
fun PdfToolsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        /* dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
             val context = LocalContext.current
             if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
         }*/

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            /*          val window = (view.context as Activity).window
                      window.statusBarColor = colorScheme.background.toArgb()
                      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme*/
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}


/*
private val DarkColorScheme = darkColorScheme(
    primary = pdfToolsDarkPrimary,
    onPrimary = pdfToolsDarkOnPrimary,
    primaryContainer = pdfToolsDarkPrimaryContainer,
    onPrimaryContainer = pdfToolsDarkOnPrimaryContainer,
    inversePrimary = pdfToolsDarkInversePrimary,
    secondary = pdfToolsDarkSecondary,
    onSecondary = pdfToolsDarkOnSecondary,
    secondaryContainer = pdfToolsDarkSecondaryContainer,
    onSecondaryContainer = pdfToolsDarkOnSecondaryContainer,
    tertiary = pdfToolsDarkTertiary,
    onTertiary = pdfToolsDarkOnTertiary,
    tertiaryContainer = pdfToolsDarkTertiaryContainer,
    onTertiaryContainer = pdfToolsDarkOnTertiaryContainer,
    error = pdfToolsDarkError,
    onError = pdfToolsDarkOnError,
    errorContainer = pdfToolsDarkErrorContainer,
    onErrorContainer = pdfToolsDarkOnErrorContainer,
    background = pdfToolsDarkBackground,
    onBackground = pdfToolsDarkOnBackground,
    surface = pdfToolsDarkSurface,
    onSurface = pdfToolsDarkOnSurface,
    inverseSurface = pdfToolsDarkInverseSurface,
    inverseOnSurface = pdfToolsDarkInverseOnSurface,
    surfaceVariant = pdfToolsDarkSurfaceVariant,
    onSurfaceVariant = pdfToolsDarkOnSurfaceVariant,
    outline = pdfToolsDarkOutline,
    surfaceTint = pdfToolsDarkSurfaceTint,
    scrim = pdfToolsDarkScrim,
    outlineVariant = pdfToolsDarkOutlineVariant,
)

private val LightColorScheme = lightColorScheme(
    primary = pdfToolsLightPrimary,
    onPrimary = pdfToolsLightOnPrimary,
    primaryContainer = pdfToolsLightPrimaryContainer,
    onPrimaryContainer = pdfToolsLightOnPrimaryContainer,
    inversePrimary = pdfToolsLightInversePrimary,
    secondary = pdfToolsLightSecondary,
    onSecondary = pdfToolsLightOnSecondary,
    secondaryContainer = pdfToolsLightSecondaryContainer,
    onSecondaryContainer = pdfToolsLightonSecondaryContainer,
    tertiary = pdfToolsLightTertiary,
    onTertiary = pdfToolsLightOnTertiary,
    tertiaryContainer = pdfToolsLightTertiaryContainer,
    onTertiaryContainer = pdfToolsLightOnTertiaryContainer,
    error = pdfToolsLightError,
    onError = pdfToolsLightOnError,
    errorContainer = pdfToolsLightErrorContainer,
    onErrorContainer = pdfToolsLightOnErrorContainer,
    background = pdfToolsLightBackground,
    onBackground = pdfToolsLightOnBackground,
    surface = pdfToolsLightSurface,
    onSurface = pdfToolsLightOnSurface,
    inverseSurface = pdfToolsLightInverseSurface,
    inverseOnSurface = pdfToolsLightInverseOnSurface,
    surfaceVariant = pdfToolsLightSurfaceVariant,
    onSurfaceVariant = pdfToolsLightOnSurfaceVariant,
    outline = pdfToolsLightOutline,
    surfaceTint = pdfToolsLightSurfaceTint,
    scrim = pdfToolsLightScrim,
    outlineVariant = pdfToolsLightOutlineVariant,
)
*/

