package com.kong.whereismyebook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kong.whereismyebook.data.repository.LibraryRepository
import com.kong.whereismyebook.model.Book
import com.kong.whereismyebook.model.LibraryWithBooks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
): ViewModel(){
    private val TAG = LibraryRepository::class.simpleName

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    val getAllLibraryWithBooks: Flow<List<LibraryWithBooks>> =
        libraryRepository.getAllLibrariesWithBooks()
            .combine(_searchQuery) { libraries, query ->
                if (query.isBlank()) {
                    libraries
                } else {
                    libraries.map {
                        it.copy(
                            books = it.books.filter { book ->
                                book.name.contains(query, ignoreCase = true)
                            }
                        )
                    }
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            libraryRepository.deleteBook(book)
        }
    }
}