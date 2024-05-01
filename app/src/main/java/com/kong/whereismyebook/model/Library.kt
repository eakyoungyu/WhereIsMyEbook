package com.kong.whereismyebook.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "library-table")
data class Library(
    @PrimaryKey val packageName: String,
    val name: String
)
