package com.kong.whereismyebook.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "book-table",
    foreignKeys = [
        ForeignKey(
            entity = Library::class,
            parentColumns = ["packageName"],
            childColumns = ["libraryPackageName"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val libraryPackageName: String
    // saved date?
)