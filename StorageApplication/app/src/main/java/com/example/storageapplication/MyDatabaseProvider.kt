package com.example.storageapplication

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class MyDatabaseProvider : ContentProvider() {
    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3

    private val authority = "com.example.storageapplication.MyDatabaseProvider"
    private val dbHelper: MyDatabaseHelper? = null

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(authority, "book", bookDir)
        uriMatcher.addURI(authority, "book/#", bookItem)
        uriMatcher.addURI(authority, "category", categoryDir)
        uriMatcher.addURI(authority, "category/#", categoryItem)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri) = when(uriMatcher.match(uri)){
        bookDir -> "vnd.android.cursor.dir/vnd.com.example.storageapplication.MyDatabaseProvider.book"
        bookItem -> "vnd.android.cursor.item/vnd.com.example.storageapplication.MyDatabaseProvider.book"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        context?.let { MyDatabaseHelper(it, "mybook.db", 21) }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = dbHelper?.readableDatabase
        val cursor = when (uriMatcher.match(uri)) {
            bookDir -> db?.query(
                "Book",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )
            bookItem -> {
                val bookId = uri.pathSegments[1]
                db?.query("Book", projection, "id=?", arrayOf(bookId), null, null, sortOrder)
            }
            else -> null
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}