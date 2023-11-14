package com.example.decathlonassignment.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.decathlonassignment.model.Item
import com.example.decathlonassignment.utility.Constant.DEBOUNCE
import com.example.decathlonassignment.utility.Constant.STARTING_KEY
import com.example.decathlonassignment.utility.Constant.products
import kotlinx.coroutines.delay
import kotlin.math.max



class StorePagingSource(private val query: String? = null) : PagingSource<Int, Item>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val startKey = params.key ?: STARTING_KEY

        val range = startKey.until(startKey + params.loadSize)

        if (startKey != STARTING_KEY) delay(DEBOUNCE)
        return LoadResult.Page(
            data = range.map { number ->
                val random = (0..2).random()
                Item(
                    id = number,
                    name = "Item $query $number ${products.get(random).first}",
                    brand = "$number _brand",
                    price = number.toFloat(),
                    imageUrl = products.get(random).second
                )
            },
            prevKey = when (startKey) {
                STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    STARTING_KEY -> null
                    else -> prevKey
                }
            },
            nextKey = range.last + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val item = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = item.id ?: STARTING_KEY - (state.config.pageSize / 2))
    }


    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}