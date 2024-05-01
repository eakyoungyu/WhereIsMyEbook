package com.kong.whereismyebook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kong.whereismyebook.model.Book
import com.kong.whereismyebook.model.Library

@Database(
    entities = [Library::class, Book::class],
    version = 1,
    exportSchema = false
)
abstract class LibraryDatabase:RoomDatabase() {
    abstract fun libraryDao(): LibraryDAO
}