package com.dicoding.onepieceapplication.ui.screen.layout

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.onepieceapplication.R
import com.dicoding.onepieceapplication.injection.Injection
import com.dicoding.onepieceapplication.model.UiState
import com.dicoding.onepieceapplication.ui.viewmodel.DetailViewModel
import com.dicoding.onepieceapplication.ui.viewmodel.ViewModelFactory

@Composable
fun detailLayer(
    id: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    Log.e(" detail screen:" ,"Received ID di $id")
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getDetail(id)
                Log.d("loading","item loading ")
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    ((data?.photoUrl ?: "Kosong")),
                    data?.name ?: "Kosong",
                    (data?.description ?: "Kosong"
                ))
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    image: String,
    name: String,
    desc: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column {
                AsyncImage(
                    model = image, contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}