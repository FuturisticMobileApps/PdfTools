package com.futuristicMobileApps.pdftools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.futuristicmobileapps.designsystem.component.BottomBarItem
import com.futuristicmobileapps.designsystem.theme.Size
import com.futuristicmobileapps.designsystem.theme.Size.corner0
import com.futuristicmobileapps.designsystem.theme.Size.corner16
import com.futuristicmobileapps.designsystem.theme.Size.corner24
import com.futuristicmobileapps.designsystem.theme.cardBackground

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

        Column {

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .shadow(elevation = 2.dp))


            BottomAppBar(
                modifier = Modifier
                    .height(Size.bottomBar)
                    .clip(
                        shape = RoundedCornerShape(
                            topEnd = corner0,
                            topStart = corner0
                        )
                    ),
                containerColor = MaterialTheme.colorScheme.cardBackground
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

}