package com.example.dogbreed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dogbreed.presentation.breedimages.BreedImageScreen
import com.example.dogbreed.presentation.home.HomeScreen
import com.example.dogbreed.ui.theme.DogBreedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedTheme {
                StartApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DogBreedTheme {
        StartApp()
    }
}

@Composable
fun StartApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable("breed-image/{Breed_Name}",
            arguments = listOf(navArgument("Breed_Name") { type = NavType.StringType })
        ) {
            BreedImageScreen(navController = navController, viewModel = hiltViewModel())
        }
    }
}