package com.futuristicmobileapps.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futuristicmobileapps.designsystem.R

@Composable
fun ToolsCard(
    modifier: Modifier = Modifier,
    @DrawableRes imageDrawable: Int,
    @StringRes toolText: Int,
    onToolClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onToolClicked() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Image(
                painter = painterResource(imageDrawable),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp),
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center,
                text = stringResource(toolText),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ToolsHeaderText(
    @StringRes name: Int,
    modifier: Modifier
) {

    Text(
        text = stringResource(id = name).toUpperCase(Locale.current),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader() {
    ToolsHeaderText(name = R.string.modify_tools, Modifier.padding(start = 10.dp))
}

@Preview(showBackground = true)
@Composable
fun PreviewToolsCard() {
    ToolsCard(
        modifier = Modifier.size(150.dp),
        imageDrawable = R.drawable.split_icon_day,
        toolText = R.string.split_pdf,
    ) {}
}
