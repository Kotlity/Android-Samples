package com.kotlity.different_screen_sizes.composables.panes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.kotlity.different_screen_sizes.R

@Composable
fun ListPane(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(dimensionResource(R.dimen._8dp)),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(dimensionResource(R.dimen._5dp), Alignment.CenterVertically),
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        items(100) {
            Text(
                modifier = contentModifier
                    .fillParentMaxWidth()
                    .clickable {
                        onItemClick("Item: $it")
                    },
                text = stringResource(R.string.list_item).plus(" $it")
            )
        }
    }
}