package com.stslex.splashgallery.ui.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.stslex.splashgallery.ui.model.collection.CollectionModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

@ExperimentalCoroutinesApi
class CollectionViewModel @Inject constructor(
    private val queryCollectionsUseCaseProvider: Provider<QueryCollectionsUseCase>
) : ViewModel() {

    private val _query = MutableStateFlow(emptyList<String>())
    val query: StateFlow<List<String>> = _query.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    val collections: StateFlow<PagingData<CollectionModel>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(query: List<String>): Pager<Int, CollectionModel> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            newPagingSource?.invalidate()
            val queryPhotosUseCase = queryCollectionsUseCaseProvider.get()
            queryPhotosUseCase(query).also { newPagingSource = it }
        }
    }

    fun setQuery(query: List<String>) {
        _query.tryEmit(query)
    }
}