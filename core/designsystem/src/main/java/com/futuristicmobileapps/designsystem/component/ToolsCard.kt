package com.futuristicmobileapps.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.futuristicmobileapps.designsystem.theme.Size.corner10
import com.futuristicmobileapps.designsystem.theme.cardBackground
import com.futuristicmobileapps.designsystem.theme.textColor

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
        shape = RoundedCornerShape(corner10),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.cardBackground),
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
                    .size(height = 42.dp, width = 36.dp),
            )

            Text(
                modifier = Modifier.padding(top = 8.dp, start = 2.dp, end = 2.dp),
                textAlign = TextAlign.Center,
                text = stringResource(toolText),
                color = MaterialTheme.colorScheme.textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PageSubHeader(
    @StringRes name: Int,
    modifier: Modifier = Modifier
) {

    Text(
        text = stringResource(id = name).toUpperCase(Locale.current),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.textColor,
        modifier = modifier
    )
}


@Composable
fun AppHeader(
    @StringRes name: Int = R.string.pdf_tools,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.pdf_icon),
            contentDescription = "pdfIcon"
        )

        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.textColor,
            modifier = modifier.padding(start = 8.dp)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHeader() {
    PageSubHeader(name = R.string.modify_tools, Modifier.padding(start = 10.dp))
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
