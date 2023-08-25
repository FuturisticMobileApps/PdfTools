package com.futuristicmobileapps.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.futuristicmobileapps.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    @StringRes text: Int,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                alpha = 0.2f
            )
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp,
            pressedElevation = 25.dp,
        ),
        onClick = onClick,
    ) {

        Text(
            text = stringResource(id = text),
            color = MaterialTheme.colorScheme.inversePrimary,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(18.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun AddRangeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Row(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.weight(1f))

        OutlinedCard(
            modifier = modifier
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
                pressedElevation = 18.dp,
            ),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
        ) {

            Row(modifier = Modifier.padding(10.dp)) {

                Image(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
                )

                Text(
                    text = "Add Range",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 4.dp)
                )

            }
        }
    }


}


@Composable
fun CircleButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {

    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = CircleShape,
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier.clickable { onClick() }
    ) {

        Image(
            modifier = Modifier.padding(4.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }

}













