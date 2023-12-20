package com.dicoding.onepieceapplication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dicoding.onepieceapplication.model.AtributeChar
import com.dicoding.onepieceapplication.repo.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: CharacterRepository) : ViewModel() {
    private val dataCharacter = MutableStateFlow(
        repository.getCharacter()
            .sortedBy { atributeCharacter ->
                atributeCharacter.name
            }
            .groupBy { atributeCharacter ->
                atributeCharacter.name[0]
            }
    )
    val groupCharacter: StateFlow<Map<Char, List<AtributeChar>>> get()= dataCharacter

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        dataCharacter.value = repository.searchHeroes(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}