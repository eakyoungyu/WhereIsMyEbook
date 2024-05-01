package com.kong.whereismyebook.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.kong.whereismyebook.model.Book
import com.kong.whereismyebook.model.LibraryWithBooks
import com.kong.whereismyebook.viewmodel.LibraryViewModel

@Composable
fun LibraryView() {
    val viewModel = hiltViewModel<LibraryViewModel>()
    val libraries = viewModel.getAllLibraryWithBooks.collectAsState(initial = listOf())

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(libraries.value) {
                lib ->
                LibraryItem(library = lib)
            }
        }
    }
}

@Composable
fun LibraryItem(library: LibraryWithBooks) {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val launchIntent = packageManager.getLaunchIntentForPackage(library.library.packageName)
    val appIcon = packageManager.getApplicationIcon(library.library.packageName).toBitmap().asImageBitmap()

    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (launchIntent != null) {
                        context.startActivity(launchIntent)
                    }
                }.padding(bottom = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                bitmap = appIcon,
                contentDescription = library.library.packageName,
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
            Text(
                text = library.library.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        library.books.forEach { book ->
            BookItem(book = book)
        }

    }
}

@Composable
fun BookItem(book: Book) {
    Text(
        text = book.name,
        modifier = Modifier.padding(bottom = 8.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}