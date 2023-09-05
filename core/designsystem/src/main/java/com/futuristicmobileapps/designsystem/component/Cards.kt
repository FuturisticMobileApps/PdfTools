package com.futuristicmobileapps.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.theme.cardBackground
import com.futuristicmobileapps.designsystem.theme.icon
import com.futuristicmobileapps.designsystem.theme.iconsBackground
import com.futuristicmobileapps.designsystem.theme.textColor
import com.futuristicmobileapps.designsystem.util.SplitRange
import com.futuristicmobileapps.designsystem.util.intToString
import com.futuristicmobileapps.designsystem.util.isValidInt
import com.futuristicmobileapps.designsystem.util.isValidString
import com.futuristicmobileapps.designsystem.util.stringToInt
import com.futuristicmobileapps.designsystem.util.validateString


@Composable
fun SplitRangeCard(
    splitRange: SplitRange,
    totalPages: Int,
    isDeleteVisible: Boolean = true,
    onDeleteClicked: () -> Unit,
    updateSplitRange: (splitRange: SplitRange) -> Unit
) {

    var fileName by remember { mutableStateOf(splitRange.fileName) }
    var pageFrom by remember { mutableStateOf(splitRange.from.intToString()) }
    var pageTo by remember { mutableStateOf(splitRange.to.intToString()) }
    var pageFromErrorMessage by remember { mutableStateOf("") }
    var pageToErrorMessage by remember { mutableStateOf("") }


    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 15.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier.padding(top = 10.dp)
    ) {

        Column(modifier = Modifier.padding(10.dp)) {

            Row(modifier = Modifier.fillMaxWidth()) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Range ${splitRange.range}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontStyle = FontStyle.Normal
                )

                if (isDeleteVisible) {
                    CircleButton(icon = R.drawable.delete_icon) {
                        onDeleteClicked()
                    }
                }

            }

            FileNameTextField(
                onValueChange = {
                    fileName = it
                    updateSplitRange(
                        SplitRange(
                            fileName,
                            pageFrom.stringToInt(),
                            pageTo.stringToInt(),
                            splitRange.range
                        )
                    )
                },
                value = fileName,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                updateSplitRange(
                    SplitRange(
                        fileName,
                        pageFrom.stringToInt(),
                        pageTo.stringToInt(),
                        splitRange.range
                    )
                )
            }

            Row(modifier = Modifier.padding(top = 10.dp)) {

                FileRangeTextField(
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        if (it.stringToInt() < totalPages)
                            pageFrom = it
                        pageFromErrorMessage = if (it.isValidInt()) {
                            updateSplitRange(
                                SplitRange(
                                    fileName,
                                    pageFrom.stringToInt(),
                                    pageTo.stringToInt(),
                                    splitRange.range
                                )
                            )
                            ""
                        } else {
                            "Invalid Entry"
                        }

                    },
                    value = pageFrom,
                    hint = R.string.from,
                    errorMessage = pageFromErrorMessage
                ) {
                    updateSplitRange(
                        SplitRange(
                            fileName,
                            pageFrom.stringToInt(),
                            pageTo.stringToInt(),
                            splitRange.range
                        )
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                FileRangeTextField(
                    onValueChange = {
                        if (it.stringToInt() <= totalPages)
                            pageTo = it
                        pageToErrorMessage = if (it.isValidInt()) {
                            updateSplitRange(
                                SplitRange(
                                    fileName,
                                    pageFrom.stringToInt(),
                                    pageTo.stringToInt(),
                                    splitRange.range
                                )
                            )
                            ""
                        } else
                            "Invalid Entry"
                    },
                    value = pageTo,
                    hint = R.string.to,
                    errorMessage = pageToErrorMessage,
                    modifier = Modifier.weight(1f)
                ) {
                    updateSplitRange(
                        SplitRange(
                            fileName,
                            pageFrom.stringToInt(),
                            pageTo.stringToInt(),
                            splitRange.range
                        )
                    )
                }
            }

        }
    }


}


@Composable
fun DetailScreenCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(topStartPercent = 12, topEndPercent = 12),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp,
            pressedElevation = 25.dp,
        ),
    ) {
        content()
    }
}

@Composable
fun FileDetailsCard(
    modifier: Modifier,
    name: String,
    date: String = "",
    size: String,
    totalPages: String? = null,
    isLoading: Boolean = false
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {


        Row(modifier = Modifier.padding(10.dp)) {

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.textColor
                )

                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = "$date, $size, $totalPages pages",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.textColor
                )
            }

            Row {

                CircleIcon(icon = R.drawable.preview_icon, modifier = Modifier.padding(end = 6.dp))

                CircleIcon(icon = R.drawable.refresh_icon)

            }


        }


    }

}

@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int
) {

    Card(
        modifier = modifier,
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.iconsBackground),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
        )
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(id = icon),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
            contentDescription = stringResource(id = R.string.round_icon)
        )
    }
}


@Composable
fun FileDetailsCard2(
    name: String,
    size: String,
    totalPages: String? = null,
    modifier: Modifier,
    isLoading: Boolean = false
) {

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
        )
    ) {


        if (isLoading) {
            Box(
                modifier = Modifier
                    .shimmerEffect()
                    .height(70.dp)
            ) { Spacer(modifier = Modifier.fillMaxSize()) }
        } else {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth()) {

                    Row(modifier = Modifier.weight(1f)) {

                        Field(label = "size :")

                        Value(value = size)
                    }

                    if (totalPages.isValidString()) {

                        Spacer(modifier = Modifier.width(4.dp))

                        Row(modifier = Modifier.align(Alignment.Bottom)) {

                            Field(label = "Total pages :")

                            Value(value = totalPages.validateString())
                        }
                    }
                }

            }

        }


    }


}

@Composable
fun PdfCard(
    name: String,
    size: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 10.dp,
        ),
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {


            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.pdf_icon),
                contentDescription = null,
            )


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Row {

                    Text(
                        text = "size : ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        text = size,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPdfCard() {
}