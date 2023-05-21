package ru.itis.harrypotterelixirs.ui.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import ru.itis.harrypotterelixirs.ui.screens.details.DetailsScreen
import ru.itis.harrypotterelixirs.ui.screens.elixirs.ElixirsScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN,
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding()) {

        mainNavGraph(navController = navController)
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.MAIN,
        startDestination = MainScreen.Main.route
    ) {

        composable(MainScreen.Main.route){
            ElixirsScreen( navController = navController )
        }
        composable(
            MainScreen.Details.route,
            arguments = listOf(
                navArgument("potionName") {
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            DetailsScreen( navController = navController, backStackEntry.arguments?.getString("potionName"))
        }
    }

}

const val DETAILS_ARG_KEY = "potionName"

sealed class MainScreen(val route: String) {
    object Main : MainScreen(route = "main")
    object Details : MainScreen(route = "details/{$DETAILS_ARG_KEY}"){
        fun passPotionName(potionName: String): String {
            return "details/$potionName"
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
}

