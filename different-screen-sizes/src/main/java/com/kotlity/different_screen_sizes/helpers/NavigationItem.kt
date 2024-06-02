package com.kotlity.different_screen_sizes.helpers

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(@StringRes val title: Int, val selectedIcon: ImageVector, val unselectedIcon: ImageVector, val badgeCount: Int? = null)