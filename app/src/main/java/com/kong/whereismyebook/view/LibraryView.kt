package com.kong.whereismyebook.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { newQuery ->
                viewModel.updateSearchQuery(newQuery)
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(libraries.value) {
                lib ->
                LibraryItem(library = lib, viewModel)
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("책 찾기") },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun LibraryItem(library: LibraryWithBooks, viewModel: LibraryViewModel) {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val launchIntent = packageManager.getLaunchIntentForPackage(library.library.packageName)
    val appIcon = packageManager.getApplicationIcon(library.library.packageName).toBitmap().asImageBitmap()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (launchIntent != null) {
                        context.startActivity(launchIntent)
                    }
                }
                .padding(bottom = 16.dp)
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
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Open App"
            )
        }


        library.books.forEach { book ->
            key (book.id) {
                BookItem(book = book, viewModel = viewModel)
            }
        }
    }
}


@Composable
fun BookItem(book: Book, viewModel: LibraryViewModel) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = book.name,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            style = MaterialTheme.typography.bodyLarge,
        )
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Book",
            modifier = Modifier.clickable {
                viewModel.deleteBook(book)
            }
        )
    }
    Spacer(modifier = Modifier.padding(bottom = 16.dp))
}