package com.futuristicmobileapps.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SectionDivider(horizontal: Dp = 36.dp, top: Dp = 12.dp, bottom: Dp = 12.dp) {

    Spacer(
        modifier = Modifier
            .padding(start = horizontal, end = horizontal, top = top, bottom = bottom)
            .fillMaxWidth()
            .height(0.8.dp)
            .shadow(elevation = 0.4.dp)
    )


}