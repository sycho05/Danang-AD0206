package com.aqua.aquascape.Provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.aqua.aquascape.Database.AquascapeDatabase
import com.aqua.aquascape.Database.Dao

class DataContentProvider : ContentProvider() {

    companion object{
        private lateinit var dao: Dao
        private const val TABLE = 1
        private const val AUTHORITY = "com.aqua.aquascape.databarang"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    init {
        uriMatcher.addURI(AUTHORITY, "barang_table", 1)
        uriMatcher.addURI(AUTHORITY,  "barang/*", 2)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        dao = AquascapeDatabase.getInstance(context!!).dao()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?,
                       sortOrder: String?): Cursor? {
        var cursor:Cursor? = null
        when (uriMatcher.match(uri)) {
            TABLE -> cursor = dao.getCursorAll()
            else -> cursor = null
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
