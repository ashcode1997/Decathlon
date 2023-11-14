package com.example.decathlonassignment.repository

class StoreRepository {
    fun getStorePagingSource(query : String) = StorePagingSource(query)
}