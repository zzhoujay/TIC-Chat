package com.zzhoujay.tic_chat.util

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

/**
 * Created by zhou on 16-4-11.
 */
object BitmapKit {
    fun saveBitmapToLocal(file: File, bitmap: Bitmap) {
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
    }
}