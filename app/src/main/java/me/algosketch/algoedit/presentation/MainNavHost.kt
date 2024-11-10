package me.algosketch.algoedit.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.algosketch.algoedit.presentation.route.MainRoute
import me.algosketch.algoedit.presentation.screen.edition.EditionScreen
import me.algosketch.algoedit.presentation.screen.home.HomeScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = MainRoute.Home
    ) {
        composable<MainRoute.Home> {
            HomeScreen(navigateToEdit = {navController.navigate(MainRoute.Edition)})
        }

        composable<MainRoute.Edition> {
            EditionScreen()
        }
    }
}