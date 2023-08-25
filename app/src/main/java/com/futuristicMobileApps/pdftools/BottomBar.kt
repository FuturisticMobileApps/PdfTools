package com.futuristicMobileApps.pdftools

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.futuristicmobileapps.designsystem.component.BottomBarItem

private val pdfToolsScreens = listOf(
    BottomAppBarScreens.Tools,
    BottomAppBarScreens.Files,
    BottomAppBarScreens.Settings
)

@Composable
fun BottomBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination =
        pdfToolsScreens.any { it.route == currentDestination?.route || "tools" == currentDestination?.route }

    if (bottomBarDestination) {
        BottomAppBar(
            modifier = Modifier.clip(shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)),
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {

            pdfToolsScreens.forEach { bottomBarItem ->

                BottomBarItem(
                    label = bottomBarItem.label,
                    drawableRes = bottomBarItem.drawable,
                    isSelected = currentDestination?.hierarchy?.any { it.route == bottomBarItem.route } == true
                ) {
                    navController.navigate(bottomBarItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }

        }
    }

}