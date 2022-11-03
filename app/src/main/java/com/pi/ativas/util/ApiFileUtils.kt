package com.pi.ativas.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

class ApiFileUtils {
    fun encode(context: Context, imageUri: Uri): String {
        val input = context.getContentResolver().openInputStream(imageUri)
        val imageOriginal = BitmapFactory.decodeStream(input , null, null)
        val image = Bitmap.createScaledBitmap(imageOriginal!!, 600, 600, true)

        // Encode image to base64 string
        val baos = ByteArrayOutputStream()
        image!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }

    fun decode(imageString: String): Bitmap {

        // Decode base64 string to image
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return decodedImage
        //imageview.setImageBitmap(decodedImage)
    }
}