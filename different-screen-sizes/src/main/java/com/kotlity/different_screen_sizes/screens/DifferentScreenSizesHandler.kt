package com.kotlity.different_screen_sizes.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kotlity.different_screen_sizes.composables.layouts.navigation.NavigationSuiteLayout
import com.kotlity.different_screen_sizes.helpers.DifferentScreenSizesLayoutType
import com.kotlity.different_screen_sizes.helpers.ListDetailNavigator
import com.kotlity.different_screen_sizes.helpers.NavigableListDetailNavigator

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DifferentScreenSizesHandler() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val listDetailNavigator: ListDetailNavigator = NavigableListDetailNavigator(navigator)
    var selectedNavigationItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavigationSuiteLayout(
            contentModifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            selectedNavigationItem = selectedNavigationItem,
            windowWidthSizeClass = windowWidthSizeClass,
            onSelectedNavigationItemChange = {
                selectedNavigationItem = it
            },
            navigator = navigator,
            listDetailNavigator = listDetailNavigator,
            differentScreenSizesLayoutType = DifferentScreenSizesLayoutType.entries[selectedNavigationItem]
        )
    }
}