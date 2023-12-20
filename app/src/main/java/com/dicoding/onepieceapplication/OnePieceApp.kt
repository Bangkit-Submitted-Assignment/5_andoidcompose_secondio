package com.dicoding.onepieceapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.onepieceapplication.ui.screen.Screen
import com.dicoding.onepieceapplication.ui.screen.layout.detailLayer
import com.dicoding.onepieceapplication.ui.screen.layout.mainLayer
import com.dicoding.onepieceapplication.ui.screen.layout.profilLayer

@Composable
fun OnePieceApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.main.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.main.route) {
//                HomeScreen(navController = navController,
//                    navigateToDetail = {
//                        navController.navigate(Screen.DetailCharacter.createRoute(it))
//
//                    }
//                )
                mainLayer(navController,{})
            }
            composable(
                route = Screen.detail.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType }),
            ){
                val id= it.arguments!!.getLong("id")
                detailLayer(id)
            }
            composable(Screen.profil.route) {
                profilLayer()
            }
        }
    }
}