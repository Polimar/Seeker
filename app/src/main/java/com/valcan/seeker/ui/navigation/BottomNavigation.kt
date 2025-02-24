package com.valcan.seeker.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigation(
    currentRoute: String,
    onNavigate: (NavigationItem) -> Unit,
    sesso: String = "M"
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        listOf(
            NavigationItem.Home,
            NavigationItem.Vestiti,
            NavigationItem.Scarpe,
            NavigationItem.Cerca,
            NavigationItem.Impostazioni
        ).forEach { item ->
            val selected = currentRoute == item::class.simpleName
            val iconSize = if (selected) 58.dp else 48.dp
            
            NavigationBarItem(
                icon = { 
                    Image(
                        painter = painterResource(id = item.getIcon(sesso)),
                        contentDescription = null,
                        modifier = Modifier.size(iconSize),
                        colorFilter = if (!selected) {
                            // Applica desaturazione del 50% alle icone non selezionate
                            ColorFilter.colorMatrix(
                                ColorMatrix().apply {
                                    setToSaturation(0.5f)
                                }
                            )
                        } else null
                    )
                },
                selected = selected,
                onClick = { onNavigate(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.surface // Rimuove lo sfondo dell'indicatore
                )
            )
        }
    }
} 