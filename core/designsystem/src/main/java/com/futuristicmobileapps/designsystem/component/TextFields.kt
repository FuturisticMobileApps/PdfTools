package com.futuristicmobileapps.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.util.isValidString


@Composable
fun FileNameTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    @StringRes hint: Int = R.string.file_name,
    shape: Shape = RoundedCornerShape(4.dp),
    onValueChange: (String) -> Unit,
    onDoneClicked: () -> Unit
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        singleLine = true,
        textStyle = MaterialTheme.typography.displaySmall.copy(color = MaterialTheme.colorScheme.onPrimary),
        label = { Text(stringResource(id = hint)) },
        trailingIcon = {
            if (value.isValidString()) Icon(
                imageVector = Icons.Default.Clear,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Clear",
                modifier = Modifier.clickable {
                    onValueChange("")
                }
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDoneClicked() }),
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileRangeTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes hint: Int = R.string.file_name,
    shape: Shape = RoundedCornerShape(4.dp),
    errorMessage : String = "",
    onValueChange: (String) -> Unit,
    onDoneClicked: () -> Unit,
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        singleLine = true,
        isError = errorMessage.isValidString(),
        supportingText = {Text(text = errorMessage)},
        textStyle = MaterialTheme.typography.displaySmall.copy(color = MaterialTheme.colorScheme.onPrimary),
        label = { Text(stringResource(id = hint)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(onDone = { onDoneClicked() }),
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.78f),
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}

@Composable
fun SubTitle(
    modifier: Modifier = Modifier,
    subTitle: String,
    textAlign: TextAlign = TextAlign.Start
) {

    Text(
        text = subTitle, style = MaterialTheme.typography.titleMedium.copy(textAlign = textAlign),
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier.padding(start = 4.dp)
    )
}

@Composable
fun PdfTitle(title: String) {

    Text(
        modifier = Modifier,
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyLarge
    )
}


@Composable
fun Field(label: String) {
    Text(
        text = label,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(end = 4.dp)
    )
}


@Composable
fun Value(value: String) {
    Text(
        text = value,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyMedium
    )
}

