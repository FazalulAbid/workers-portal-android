package com.fifty.workersportal.core.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

fun contentUriToFileUri(context: Context, contentUri: Uri): Uri? {
    val contentResolver: ContentResolver = context.contentResolver
    val returnCursor = contentResolver.query(contentUri, null, null, null, null)
    returnCursor?.let {
        val nameIndex = it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
        it.moveToFirst()
        val name = it.getString(nameIndex)

        val file = File(context.cacheDir, name)
        val outputStream = file.outputStream()
        val inputStream = contentResolver.openInputStream(contentUri)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return file.toUri()
    }
    return null
}