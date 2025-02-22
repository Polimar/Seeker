package com.valcan.seeker.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigation(
    currentRoute: String,
    onNavigate: (NavigationItem) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        listOf(
            NavigationItem.Home,
            NavigationItem.Vestiti,
            NavigationItem.Armadio,
            NavigationItem.Cerca,
            NavigationItem.Impostazioni
        ).forEach { item ->
            NavigationBarItem(
                icon = { 
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                },
                selected = currentRoute == item::class.simpleName,
                onClick = { onNavigate(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
} 