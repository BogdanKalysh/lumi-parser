package com.challange.lumiparser.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.challange.lumiparser.ui.screen.FullImageScreen
import com.challange.lumiparser.ui.screen.MainScreen
import com.challange.lumiparser.viewmodel.MainViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: MainViewModel, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(viewModel, navController, modifier)
        }
        composable(
            "full_image_screen/{imageUrl}/{title}"
        ) { backStackEntry ->
            val imageUrl = URLDecoder.decode(backStackEntry.arguments?.getString("imageUrl")!!, StandardCharsets.UTF_8.toString())
            val title = backStackEntry.arguments?.getString("title")!!
            FullImageScreen(imageUrl, title, navController, modifier)
        }
    }
}