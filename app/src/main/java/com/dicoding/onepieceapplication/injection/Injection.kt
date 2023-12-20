package com.dicoding.onepieceapplication.injection

import com.dicoding.onepieceapplication.repo.CharacterRepository

object Injection {
    fun provideRepository(): CharacterRepository {
        return CharacterRepository.getInstance()
    }
}