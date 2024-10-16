package com.blueland.androidcomposenavigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blueland.androidcomposenavigation.model.UserModel
import com.blueland.androidcomposenavigation.ui.AScreen
import com.blueland.androidcomposenavigation.ui.BScreen
import com.blueland.androidcomposenavigation.ui.CScreen
import kotlinx.serialization.json.Json

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.AScreen.route) {
        composable(route = Route.AScreen.route) {
            AScreen(navController = navController)
        }
        composable(
            route = Route.BScreen.route,
            arguments = listOf(
                navArgument(NavigationArgs.INT_PARAM) { type = NavType.IntType },
                navArgument(NavigationArgs.STRING_PARAM) { type = NavType.StringType },
                navArgument(NavigationArgs.BOOL_PARAM) { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val intParam = backStackEntry.arguments?.getInt(NavigationArgs.INT_PARAM)
            val stringParam = backStackEntry.arguments?.getString(NavigationArgs.STRING_PARAM)
            val boolParam = backStackEntry.arguments?.getBoolean(NavigationArgs.BOOL_PARAM)

            // 매개변수로 BScreen에 값 전달
            BScreen(
                navController = navController,
                intParam = intParam ?: 0,
                stringParam = stringParam.orEmpty(),
                boolParam = boolParam ?: false
            )
        }
        composable(
            route = Route.CScreen.route,
            arguments = listOf(
                navArgument(NavigationArgs.USER_MODEL_PARAM) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val userModelJson = backStackEntry.arguments?.getString(NavigationArgs.USER_MODEL_PARAM)
            val userModel = userModelJson?.let { Json.decodeFromString<UserModel>(it) } // JSON 문자열을 UserModel로 역직렬화
            CScreen(navController = navController, userModel = userModel)
        }
    }
}

