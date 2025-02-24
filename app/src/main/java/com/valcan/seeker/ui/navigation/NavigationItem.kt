package com.valcan.seeker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.valcan.seeker.R

sealed class NavigationItem(val getIcon: (String) -> Int) {
    object Home : NavigationItem({ _ -> R.drawable.ic_home })
    object Vestiti : NavigationItem({ _ -> R.drawable.ic_hanger })
    object Scarpe : NavigationItem({ sesso -> 
        if (sesso == "M") R.drawable.msneakers else R.drawable.fshoe 
    })
    object Armadio : NavigationItem({ _ -> R.drawable.ic_wardrobe })
    object Cerca : NavigationItem({ _ -> R.drawable.ic_search })
    object Impostazioni : NavigationItem({ _ -> R.drawable.ic_settings })
} 