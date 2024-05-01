package com.kong.whereismyebook.model

import androidx.room.Embedded
import androidx.room.Relation


data class LibraryWithBooks(
    @Embedded val library: Library,
    @Relation(
        parentColumn = "packageName",
        entityColumn = "libraryPackageName"
    )
    val books: List<Book>
)
