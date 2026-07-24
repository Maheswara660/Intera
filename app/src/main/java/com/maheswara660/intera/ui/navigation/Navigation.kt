package com.maheswara660.intera.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.maheswara660.intera.ui.components.accentGradient
import com.maheswara660.intera.ui.screens.*
import com.maheswara660.intera.ui.theme.*

// ═══════════════════════════════════════════════
// Route definitions
// ═══════════════════════════════════════════════

sealed class Screen(val route: String) {
    object Home    : Screen("home")
    object Details : Screen("details/{projectId}") {
        fun createRoute(id: Int) = "details/$id"
    }
    object Settings : Screen("settings")
}

// ═══════════════════════════════════════════════
// Bottom Nav items
// ═══════════════════════════════════════════════

data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

private val bottomNavItems = listOf(
    BottomNavItem(Screen.Home.route, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(Screen.Details.route, "Details", Icons.Filled.Dashboard, Icons.Outlined.Dashboard),
    BottomNavItem(Screen.Settings.route, "Settings", Icons.Filled.Settings, Icons.Outlined.Settings),
)

// ═══════════════════════════════════════════════
// Main Navigation Host
// ═══════════════════════════════════════════════

@Composable
fun InteraNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Hide bottom bar on details when coming from home (show it as a standalone tab too)
    val showBottomBar = currentRoute != null

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkNavy,
        bottomBar = {
            if (showBottomBar) {
                InteraBottomBar(navController = navController, currentRoute = currentRoute)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300),
                ) + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it / 3 },
                    animationSpec = tween(300),
                ) + fadeOut(tween(200))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it / 3 },
                    animationSpec = tween(300),
                ) + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300),
                ) + fadeOut(tween(200))
            },
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onProjectClick = { project ->
                        navController.navigate(Screen.Details.createRoute(project.id))
                    }
                )
            }

            composable(
                route = Screen.Details.route,
                arguments = listOf(navArgument("projectId") { type = NavType.IntType }),
            ) { backStack ->
                val id = backStack.arguments?.getInt("projectId") ?: 1
                val project = sampleProjects.firstOrNull { it.id == id } ?: sampleProjects[0]
                DetailsScreen(
                    project = project,
                    onBack = { navController.popBackStack() },
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

// ═══════════════════════════════════════════════
// Custom bottom navigation bar
// ═══════════════════════════════════════════════

@Composable
private fun InteraBottomBar(
    navController: NavController,
    currentRoute: String?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkSurface)
            .navigationBarsPadding(),
    ) {
        // Top neon divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            NeonGreen.copy(alpha = 0.0f),
                            NeonGreen.copy(alpha = 0.5f),
                            ElectricBlue.copy(alpha = 0.5f),
                            ElectricBlue.copy(alpha = 0.0f),
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            bottomNavItems.forEach { item ->
                // Match top-level routes (details/{id} → details/)
                val isSelected = when {
                    item.route == Screen.Details.route ->
                        currentRoute?.startsWith("details/") == true
                    else -> currentRoute == item.route
                }

                BottomNavItemView(
                    item = item,
                    isSelected = isSelected,
                    onClick = {
                        val targetRoute = when (item.route) {
                            Screen.Details.route -> Screen.Details.createRoute(sampleProjects.first().id)
                            else -> item.route
                        }
                        navController.navigate(targetRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .run {
                if (isSelected) {
                    this.background(
                        Brush.verticalGradient(
                            listOf(NeonGreen.copy(alpha = 0.12f), ElectricBlue.copy(alpha = 0.08f))
                        )
                    ).border(
                        1.dp,
                        Brush.linearGradient(listOf(NeonGreen.copy(0.3f), ElectricBlue.copy(0.3f))),
                        RoundedCornerShape(16.dp),
                    )
                } else this
            }
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
    ) {
        Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.label,
            tint = if (isSelected) NeonGreen else TextDisabled,
            modifier = Modifier.size(22.dp),
        )
        Spacer(Modifier.height(3.dp))
        Text(
            text = item.label,
            fontSize = 10.sp,
            color = if (isSelected) NeonGreen else TextDisabled,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            letterSpacing = 0.2.sp,
        )
    }
}
