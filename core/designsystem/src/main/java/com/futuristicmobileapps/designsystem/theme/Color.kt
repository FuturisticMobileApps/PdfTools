package com.futuristicmobileapps.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


// New Colors
val backgroundEndLight = Color(0xFFf5f5f5)
val backgroundStartLight = Color(0xFFe7e6e2)
val backgroundDark = Color(0xFF2b2c32)
val primaryOrange = Color(0xFFFD694c)
val cardBackgroundLight = Color(0xFFfafeff)
val cardBackgroundDark = Color(0xFF353840)
val containerLight = Color(0xFFffeae6)

//val selectedIconLight = Color(0xFFFD694c)
val selectedIconLight = primaryOrange
val unselectedIconLight = Color(0xFFc2c8d6)
val textColorLight = Color(0xFF36314e)
val textColorDark = Color(0xFFe7e7e7)
val subTitleLight = Color(0xFFa1a9ad)
val subTitleDark = Color(0xFFD3E0EA )
val iconColor = Color(0xFFc69c84)
val iconColorLight = Color(0xFFce8761)
val iconColorDark = Color(0xFF7D4A2A )
val iconBackgroundLight = Color(0xFFfbfaf8)
val iconBackgroundDark = Color(0xFF3A3A3A)

val orange = Color(0xFFFD694c)
val lightBlack = Color(0xFF4D4867)
val lightYellow = Color(0xFFFFD88D)


val DeepNavyBlue = Color(0xFF003366)
val SteelGray = Color(0xFF808080)
val LightGray = Color(0xFFF0F0F0)
val Charcoal = Color(0xFF333333)
val White = Color.White


val lightPrimary = Color(0xFFe18138)
val lightBackground = Color(0xFFf9fafe)
val lightSurfaceVariant = Color.White

val darkPrimary = Color(0xFFFFEDE4)
val darkBackground = Color(0xFF2b2c32)


val backgroundStart: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) backgroundStartLight else backgroundDark

val backgroundEnd: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) backgroundEndLight else backgroundDark

val ColorScheme.cardBackground: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) cardBackgroundLight else cardBackgroundDark

val ColorScheme.container: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) containerLight else containerLight

val ColorScheme.selectedIcon: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) selectedIconLight else selectedIconLight

val ColorScheme.unSelectedIcon: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) unselectedIconLight else unselectedIconLight

val ColorScheme.textColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) textColorLight else textColorDark

val ColorScheme.subTextColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) subTitleLight else subTitleDark

val ColorScheme.icon: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) iconColorLight else iconColorDark

val ColorScheme.iconsBackground: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) iconBackgroundLight else iconBackgroundDark


// GradientColors
val backgroundBrush: Brush
    @Composable
    get() = Brush.horizontalGradient(
        listOf(
            backgroundStart,
            backgroundEnd
        )
    )


/*
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
*/







