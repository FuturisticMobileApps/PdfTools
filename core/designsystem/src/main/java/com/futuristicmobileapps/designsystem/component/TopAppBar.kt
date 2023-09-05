package com.futuristicmobileapps.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futuristicmobileapps.designsystem.R


@Composable
fun PageHeader(
    @StringRes title: Int,
    color : Color,
    onBackClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 22.dp)
    ) {

        Image(
            modifier = Modifier
                .size(22.dp)
                .align(Alignment.CenterVertically)
                .clickable { onBackClick() },
            painter = painterResource(id = R.drawable.back_icon),
            colorFilter = ColorFilter.tint(color = color),
            contentDescription = stringResource(id = R.string.back_icon)
        )

        Text(
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f),
            color = color,
            text = stringResource(id = title),
            style = MaterialTheme.typography.headlineMedium
        )


    }
}


@Composable
fun ToolDetailTopBar(
    @StringRes title: Int,
    onBackClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(all = 20.dp)
    ) {

        Image(
            modifier = Modifier
                .size(22.dp)
                .align(Alignment.CenterVertically)
                .clickable { onBackClick() },
            painter = painterResource(id = R.drawable.back_icon),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f),
            text = stringResource(id = title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall
        )


    }
}

@Composable
fun ToolsTopBar(@StringRes title: Int) {

    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
            .padding(top = 20.dp, bottom = 20.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f),
            text = stringResource(id = title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBar() {
    ToolDetailTopBar(R.string.split_pdf) {

    }
}