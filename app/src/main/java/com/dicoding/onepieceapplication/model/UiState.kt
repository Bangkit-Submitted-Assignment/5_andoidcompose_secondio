package com.dicoding.onepieceapplication.model

sealed class UiState<out T: Any?> {

    object Loading : UiState<Nothing>()

    data class Success<out T: Any>(val data: AtributeChar?) : UiState<T>()

    data class Error(val errorMessage: String) : UiState<Nothing>()
}