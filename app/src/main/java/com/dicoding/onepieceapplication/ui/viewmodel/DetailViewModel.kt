package com.dicoding.onepieceapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.onepieceapplication.model.DataChar
import com.dicoding.onepieceapplication.model.UiState
import com.dicoding.onepieceapplication.repo.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<DataChar>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DataChar>>
        get() = _uiState

    fun getDetail(id: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getDetail(id))
        }
    }
}