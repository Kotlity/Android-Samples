package com.kotlity.different_screen_sizes.helpers

import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole

interface ListDetailNavigator {

    val currentPaneData: Any?

    fun navigateTo(pane: ThreePaneScaffoldRole, content: Any?)
}