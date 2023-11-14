package com.example.decathlonassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.decathlonassignment.model.Item
import com.example.decathlonassignment.repository.StoreRepository
import com.example.decathlonassignment.utility.Constant
import com.example.decathlonassignment.utility.Constant.DEBOUNCE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest


class StoreViewModel(repository: StoreRepository) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    val items : Flow<PagingData<Item>> = searchQuery.debounce(DEBOUNCE).flatMapLatest {
        Pager(
            config = PagingConfig(pageSize = Constant.ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { repository.getStorePagingSource(it) }
        ).flow.cachedIn(viewModelScope)
    }

    fun setSearch(query : String){
        searchQuery.value = query
    }
}