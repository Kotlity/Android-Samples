package com.kotlity.different_screen_sizes.helpers

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
class NavigableListDetailNavigator(private val navigator: ThreePaneScaffoldNavigator<Any>): ListDetailNavigator {

    override val currentPaneData: Any?
        get() = navigator.currentDestination?.content

    override fun navigateTo(pane: ThreePaneScaffoldRole, content: Any?) {
        navigator.navigateTo(pane, content)
    }
}