package com.example.dogbreed.presentation.breedimages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.dogbreed.presentation.home.*
import com.example.dogbreed.presentation.home.DogBreedHeader
import com.example.dogbreed.presentation.home.Error
import com.example.dogbreed.presentation.home.Loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedImageScreen(
    navController: NavController,
    viewModel: BreedImageViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        content = {
            it.calculateBottomPadding()
            when (val uiStateValue = uiState.value) {
                is BreedImageUiModel.Success -> BreedImage(
                    breedName = viewModel.breedName,
                    imageUrls = uiStateValue.data,
                    modifier = Modifier.padding(8.dp)
                )
                is BreedImageUiModel.Error -> Error(message = uiStateValue.exception?.message)
                BreedImageUiModel.Loading -> Loading()
            }
        })

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchBreedImages()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedImage(
    breedName: String,
    imageUrls: List<String>,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        DogBreedHeader(breedName, { /* oped out*/ })

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(imageUrls) { image ->
                Card(Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}