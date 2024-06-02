package com.kotlity.different_screen_sizes.composables.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableSupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kotlity.different_screen_sizes.composables.panes.DetailPane
import com.kotlity.different_screen_sizes.composables.panes.ExtraPane
import com.kotlity.different_screen_sizes.composables.panes.ListPane
import com.kotlity.different_screen_sizes.helpers.ListDetailNavigator

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListSupportingLayout(
    modifier: Modifier = Modifier,
    navigator: ThreePaneScaffoldNavigator<Any>,
    listDetailNavigator: ListDetailNavigator
) {
    NavigableSupportingPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        mainPane = {
            ListPane(
                modifier = Modifier.fillMaxSize(),
                onItemClick = { item ->
                    listDetailNavigator.navigateTo(
                        pane = SupportingPaneScaffoldRole.Supporting,
                        content = item
                    )
                }
            )
        },
        supportingPane = {
            val detailData = listDetailNavigator.currentPaneData
            DetailPane(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                listItemData = detailData,
                onOption1Click = { option1 ->
                    listDetailNavigator.navigateTo(
                        pane = SupportingPaneScaffoldRole.Extra,
                        content = option1
                    )
                },
                onOption2Click = { option2 ->
                    listDetailNavigator.navigateTo(
                        pane = SupportingPaneScaffoldRole.Extra,
                        content = option2
                    )
                }
            )
        },
        extraPane = {
            val extraData = listDetailNavigator.currentPaneData
            ExtraPane(
                modifier = Modifier.fillMaxSize(),
                detailData = extraData
            )
        }
    )
}