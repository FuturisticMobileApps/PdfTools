package com.futuristicmobileapps.designsystem.component


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

@Composable
fun RowScope.BottomBarItem(
    label: String,
    @DrawableRes drawableRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = label, style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary
            )
        },
        icon = {
            Image(
                painter = painterResource(id = drawableRes), contentDescription = null,
                colorFilter = ColorFilter.tint(
                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            )
        },
        colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.secondaryContainer)
    )
}