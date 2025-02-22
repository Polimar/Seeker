package com.valcan.seeker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.valcan.seeker.R

sealed class NavigationItem(val icon: Int) {
    object Home : NavigationItem(R.drawable.ic_home)
    object Vestiti : NavigationItem(R.drawable.ic_hanger)
    object Armadio : NavigationItem(R.drawable.ic_wardrobe)
    object Cerca : NavigationItem(R.drawable.ic_search)
    object Impostazioni : NavigationItem(R.drawable.ic_settings)
} 