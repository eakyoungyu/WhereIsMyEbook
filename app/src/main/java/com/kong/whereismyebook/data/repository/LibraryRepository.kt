package com.kong.whereismyebook.data.repository

import androidx.room.Query
import com.kong.whereismyebook.data.local.LibraryDAO
import com.kong.whereismyebook.model.Book
import com.kong.whereismyebook.model.Library
import com.kong.whereismyebook.model.LibraryWithBooks
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LibraryRepository @Inject constructor(private val libraryDao: LibraryDAO) {
    suspend fun addLibrary(library: Library) {
        libraryDao.addLibrary(library)
    }

    suspend fun addBook(book: Book) {
        libraryDao.addBook(book)
    }

    fun getAllLibrariesWithBooks(): Flow<List<LibraryWithBooks>> {
        return libraryDao.getAllLibrariesWithBooks()
    }

    fun deleteAllLibraries() {
        libraryDao.deleteAllLibraries()
    }

    fun searchLibraryByPackageName(packageName: String): Flow<Library> {
        return libraryDao.searchLibraryByPackageName(packageName)
    }
}