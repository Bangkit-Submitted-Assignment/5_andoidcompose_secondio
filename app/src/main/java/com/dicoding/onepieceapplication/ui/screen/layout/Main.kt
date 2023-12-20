package com.dicoding.onepieceapplication.ui.screen.layout

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dicoding.onepieceapplication.R
import com.dicoding.onepieceapplication.repo.CharacterRepository
import com.dicoding.onepieceapplication.ui.screen.Screen
import com.dicoding.onepieceapplication.ui.viewmodel.HomeViewModel
import com.dicoding.onepieceapplication.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun mainLayer(
    navController: NavHostController,
    navigateToDetail: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            CharacterRepository()
        )
    )
){
    val list by viewModel.groupCharacter.collectAsState()
    val query by viewModel.query

    Box(modifier = Modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 50.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                TopBar(navController = navController)
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                )
            }

            if (list.isNotEmpty()) {
                list.forEach { (char, dataCharacter) ->
                    items(dataCharacter, key = { it.id }) { character ->
                        OnePieceItem(
                            id = character.id,
                            name = character.name,
                            photo = character.photoUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(tween(durationMillis = 100))
                                .clickable {
                                    navigateToDetail(character.id)
                                },
                            navController = navController
                        )
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Tidak ada karakter \"$query\"")
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
fun OnePieceItem(id:Long,
                name: String,
                photo: String,
                modifier: Modifier = Modifier,
                navController: NavHostController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            navController.navigate(Screen.detail.createRoute(id))
            Log.d("JujutsuApp", "Character clicked: $id")
        }
    ) {
        AsyncImage(
            model = photo, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 15.dp)
        )
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_character))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.profil.route)
            }) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "about_page")
            }
        }
    )
}