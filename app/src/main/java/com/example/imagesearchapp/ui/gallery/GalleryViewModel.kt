package com.example.imagesearchapp.ui.gallery

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imagesearchapp.data.UnsplashPhoto
import com.example.imagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    unsplashRepository: UnsplashRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val currentQuery = savedStateHandle.getLiveData(KEY_CURRENT_QUERY, DEFAULT_QUERY)

    val photos: Flow<PagingData<UnsplashPhoto>> =
        unsplashRepository
            .getSearchResults(currentQuery.value ?: DEFAULT_QUERY)
            .cachedIn(viewModelScope)


    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        const val DEFAULT_QUERY = "dogs"
        const val KEY_CURRENT_QUERY = "current_query"
    }
}