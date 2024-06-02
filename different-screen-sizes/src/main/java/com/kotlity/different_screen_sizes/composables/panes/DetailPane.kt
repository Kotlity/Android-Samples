package com.kotlity.different_screen_sizes.composables.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.kotlity.different_screen_sizes.R
import com.kotlity.different_screen_sizes.helpers.dataCuster

@Composable
fun DetailPane(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    listItemData: Any? = null,
    onOption1Click: (String) -> Unit,
    onOption2Click: (String) -> Unit
) {
    val contentTextColor = MaterialTheme.colorScheme.onSecondaryContainer

    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    ) {
        Text(
            text = listItemData.dataCuster("Default item choosed"),
            color = contentTextColor
        )
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._5dp))) {
            AssistChip(
                onClick = {
                    onOption1Click("Option 1")
                },
                label = {
                    Text(
                        text = stringResource(R.string.option_1),
                        color = contentTextColor
                    )
                }
            )
            AssistChip(
                onClick = {
                    onOption2Click("Option 2")
                },
                label = {
                    Text(
                        text = stringResource(R.string.option_2),
                        color = contentTextColor
                    )
                }
            )
        }
    }
}