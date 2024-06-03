package com.example.adminapp.navigation

import ProductListScreen
import UserDetailScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.adminapp.screens.product.ProductAddScreen
import com.example.adminapp.screens.product.ProductViewModel
import com.example.adminapp.screens.user.UserListScreen
import com.example.adminapp.screens.user.UserViewModel


@Composable
fun NavGraph(navController: NavHostController) {
    val userViewModel = hiltViewModel<UserViewModel>()
    val productViewModel = hiltViewModel<ProductViewModel>()
    NavHost(navController, startDestination = Routes.UserListScreen) {

        composable<Routes.UserListScreen> {
            UserListScreen(userViewModel = userViewModel, navController = navController)
        }
        composable<Routes.UserDetailScreen> { backStackEntry ->
            val route: Routes.UserDetailScreen = backStackEntry.toRoute()
            UserDetailScreen(navController, route.userId)
        }
        composable<Routes.ProductListScreen> {
            ProductListScreen(navController)
        }
        composable<Routes.ProductAddScreen> {
            ProductAddScreen(navController)
        }

    }
}
