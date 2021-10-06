package com.example.imagesearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.imagesearchapp.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

private const val PAGE_SIZE = 20
private const val MAX_SIZE = 100

@Singleton
class UnsplashRepository @Inject constructor(
    private val unsplashApi: UnsplashApi
) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).flow
}