package com.dicoding.onepieceapplication.repo

import com.dicoding.onepieceapplication.model.AtributeChar
import com.dicoding.onepieceapplication.model.DataChar

class CharacterRepository {

    fun getCharacter(): List<AtributeChar> {
        return DataChar.dataCharacter
    }

    fun searchHeroes(query: String): List<AtributeChar> {
        return DataChar.dataCharacter.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getDetail(id: Long): AtributeChar? {
        return DataChar.dataCharacter.firstOrNull(){
            it.id==id
        }
    }


    companion object {
        @Volatile
        private var instance: CharacterRepository? = null

        fun getInstance(): CharacterRepository =
            instance ?: synchronized(this) {
                CharacterRepository().apply {
                    instance = this
                }
            }
    }
}