package com.kong.whereismyebook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kong.whereismyebook.data.repository.LibraryRepository
import com.kong.whereismyebook.model.Book
import com.kong.whereismyebook.model.LibraryWithBooks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
): ViewModel(){
    private val TAG = LibraryRepository::class.simpleName
    val getAllLibraryWithBooks: Flow<List<LibraryWithBooks>> = libraryRepository.getAllLibrariesWithBooks()
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            libraryRepository.deleteBook(book)
        }
    }
}