package com.kotlity.different_screen_sizes.composables.layouts.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.window.core.layout.WindowWidthSizeClass
import com.kotlity.different_screen_sizes.R
import com.kotlity.different_screen_sizes.composables.layouts.ListDetailLayout
import com.kotlity.different_screen_sizes.composables.layouts.ListSupportingLayout
import com.kotlity.different_screen_sizes.helpers.DifferentScreenSizesLayoutType
import com.kotlity.different_screen_sizes.helpers.ListDetailNavigator
import com.kotlity.different_screen_sizes.helpers.NavigationItem

private val navigationItems = listOf(
    NavigationItem(
        title = R.string.list_detail_screen_route,
        selectedIcon = Icons.Default.List,
        unselectedIcon = Icons.Outlined.List
    ),
    NavigationItem(
        title = R.string.list_support_screen_route,
        selectedIcon = Icons.Default.Build,
        unselectedIcon = Icons.Outlined.Build,
        badgeCount = 15
    )
)

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavigationSuiteLayout(
    contentModifier: Modifier = Modifier,
    selectedNavigationItem: Int,
    windowWidthSizeClass: WindowWidthSizeClass,
    onSelectedNavigationItemChange: (Int) -> Unit,
    navigator: ThreePaneScaffoldNavigator<Any>,
    listDetailNavigator: ListDetailNavigator,
    differentScreenSizesLayoutType: DifferentScreenSizesLayoutType
) {
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            navigationItems.forEachIndexed { index, navigationItem ->
                val isSelected = selectedNavigationItem == index
                item(
                    selected = isSelected,
                    onClick = {
                        onSelectedNavigationItemChange(index)
                    },
                    icon = {
                        Icon(
                            imageVector = if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(id = navigationItem.title))
                    },
                    badge = {
                        navigationItem.badgeCount?.let { count ->
                            Badge(
                                content = {
                                    Text(text = count.toString())
                                }
                            )
                        }
                    }
                )
            }
        },
        layoutType = if (windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())
        },
        content = {
            when(differentScreenSizesLayoutType) {
                DifferentScreenSizesLayoutType.LIST_DETAIL -> ListDetailLayout(
                    modifier = contentModifier,
                    navigator = navigator,
                    listDetailNavigator = listDetailNavigator
                )
                DifferentScreenSizesLayoutType.SUPPORTING -> ListSupportingLayout(
                    modifier = contentModifier,
                    navigator = navigator,
                    listDetailNavigator = listDetailNavigator
                )
            }
        }
    )
}