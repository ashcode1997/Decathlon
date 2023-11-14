package com.example.decathlonassignment.di

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.decathlonassignment.repository.StoreRepository

object Injection {

    private fun provideStoreRepository(): StoreRepository = StoreRepository()

    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideStoreRepository())
    }
}