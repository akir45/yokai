package eu.kanade.tachiyomi.util.system

import android.content.Context
import android.os.Build
import android.os.FileUtils
import android.os.ParcelFileDescriptor
import com.hippo.unifile.UniFile
import java.io.BufferedOutputStream
import java.io.File
import java.nio.channels.SeekableByteChannel

val UniFile.nameWithoutExtension: String?
    get() = name?.substringBeforeLast('.')

val UniFile.extension: String?
    get() = name?.replace("${nameWithoutExtension.orEmpty()}.", "")

fun UniFile.toTempFile(context: Context): File {
    val inputStream = context.contentResolver.openInputStream(uri)!!
    val tempFile =
        File.createTempFile(
            nameWithoutExtension.orEmpty(),
            null,
        )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        FileUtils.copy(inputStream, tempFile.outputStream())
    } else {
        BufferedOutputStream(tempFile.outputStream()).use { tmpOut ->
            inputStream.use { input ->
                val buffer = ByteArray(8192)
                var count: Int
                while (input.read(buffer).also { count = it } > 0) {
                    tmpOut.write(buffer, 0, count)
                }
            }
        }
    }

    return tempFile
}

fun UniFile.writeText(string: String, onComplete: () -> Unit = {}) {
    this.openOutputStream().use {
        it.write(string.toByteArray())
        onComplete()
    }
}

fun UniFile.openReadOnlyChannel(context: Context): SeekableByteChannel {
    return ParcelFileDescriptor.AutoCloseInputStream(context.contentResolver.openFileDescriptor(uri, "r")).channel
}
