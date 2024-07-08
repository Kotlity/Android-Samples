package com.kotlity.network.view.composables.generic

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FakeApiExposedDropdownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    label: String,
    currentSelectedItem: T,
    onDismissRequest: () -> Unit,
    itemsList: List<T>,
    onItemClicked: (T) -> Unit,
    itemText: @Composable (T) -> Unit,
    leadingIcon: @Composable ((T) -> Unit)? = null,
    trailingIcon: @Composable ((T) -> Unit)? = null
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = currentSelectedItem.toString(),
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = label)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            itemsList.forEach { content ->
                DropdownMenuItem(
                    text = {
                        itemText(content)
                    },
                    onClick = {
                        onItemClicked(content)
                    },
                    leadingIcon = {
                        leadingIcon?.let { it(content) }
                    },
                    trailingIcon = {
                        trailingIcon?.let { it(content) }
                    }
                )
            }
        }
    }
}