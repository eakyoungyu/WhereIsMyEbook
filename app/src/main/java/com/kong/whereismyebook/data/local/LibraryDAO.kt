package com.kong.whereismyebook.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kong.whereismyebook.model.Book
import com.kong.whereismyebook.model.Library
import com.kong.whereismyebook.model.LibraryWithBooks
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LibraryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addLibrary(library: Library)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addBook(book: Book)

    @Transaction
    @Query("SELECT * FROM `library-table`")
    abstract fun getAllLibrariesWithBooks(): Flow<List<LibraryWithBooks>>

    @Query("SELECT * FROM `book-table`")
    abstract fun getAllBooks(): Flow<List<Book>>

    @Query("DELETE FROM `library-table`")
    abstract fun deleteAllLibraries()

    @Query("SELECT * FROM `library-table` WHERE packageName=:packageName")
    abstract fun searchLibraryByPackageName(packageName: String): Flow<Library>

    @Query("SELECT * FROM `book-table` WHERE libraryPackageName=:packageName and name=:bookName")
    abstract fun searchBookFromLibrary(packageName: String, bookName: String): Flow<Book>

    @Delete
    abstract suspend fun deleteBook(book: Book)
}