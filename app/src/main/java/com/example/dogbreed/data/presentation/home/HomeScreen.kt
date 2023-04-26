package com.example.dogbreed.data.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        content = {
            when (val uiStateValue = uiState.value) {
                is HomeUiModel.Success -> DogBreeds(
                    dogBreeds = uiStateValue.data,
                    modifier = Modifier.padding(8.dp)
                )
                is HomeUiModel.Error -> Error(message = uiStateValue.exception?.message)
                HomeUiModel.Loading -> Loading()
            }
        })

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchDogBreeds()
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Error(message: String?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message ?: "Unknown error!",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun DogBreeds(
    dogBreeds: HashMap<String, List<String>>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            dogBreeds.forEach { (title, breeds) ->
                if (breeds.isNotEmpty()) {
                    item {
                        DogBreedHeader(header = title)
                    }
                    items(breeds) { breedName ->
                        DogBreedItem(
                            dogBreedName = breedName,
                            modifier = Modifier.padding(start = 24.dp),
                            onItemClicked = {
                                TODO("implement on dog breed clicked")
                            },
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun DogBreedItem(
    dogBreedName: String,
    onItemClicked: (breedName: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .clickable { onItemClicked(dogBreedName) },
        color = MaterialTheme.colorScheme.tertiary,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = dogBreedName,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun DogBreedHeader(header: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = header,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
