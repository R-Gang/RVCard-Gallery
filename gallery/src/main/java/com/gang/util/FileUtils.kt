package com.gang.util

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.gang.library.common.utils.LogUtils
import org.jetbrains.annotations.Nullable
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by haoruigang on 1/19/16.
 */
object FileUtils {

    var TAG = "FILEUTILS"

    // 图片最大大小 100KB 超过此大小要进行压缩
    var MAX_PIC_MEMORY_SIZE = 1500

    //最大尺寸 w
    var MAX_PIC_WIDTH = 720

    //最大尺寸 h
    var MAX_PIC_HEIGHT = 480

    //长图的最大宽度
    var LONG_PIC_MAX_WIDTH = 100

    fun getBasePath(context: Context?): String {
        return getStoragePath("123")
    }

    fun getStoragePath(baseName: String): String {
        var path = ""
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStorageDirectory().absolutePath
            }
        }
        val file = File("$path/$baseName")
        LogUtils.d(TAG, file.path)
        LogUtils.d(TAG, file.absolutePath)
        file.mkdirs()
        return file.absolutePath + "/"
    }

    @JvmOverloads
    fun createImageFile(context: Context?, @Nullable name: String? = null): File {
        // Create an image file name
        var name = name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        name = if (TextUtils.isEmpty(name)) timeStamp else ""
        val file = File(getBasePath(context) + name + ".jpg")
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    fun compressBitmapToFile(bmp: Bitmap, outFile: String?) {
        try {
            val baos = ByteArrayOutputStream()
            var options = 100
            bmp.compress(Bitmap.CompressFormat.PNG, options, baos)
            while (baos.toByteArray().size / 1024 > MAX_PIC_MEMORY_SIZE && options > 0) {
                baos.reset()
                options -= 10
                bmp.compress(Bitmap.CompressFormat.JPEG, options, baos)
            }
            val fos = FileOutputStream(outFile)
            fos.write(baos.toByteArray())
            fos.flush()
            fos.close()
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 生成图片，返回图片本地路径
     *
     * @param context
     * @param bitmap
     * @param mOnImageSavedCallback
     */
    fun generateImage(
        context: Context?,
        bitmap: Bitmap,
        mOnImageSavedCallback: OnImageSavedCallback?,
    ) {
        Thread {
            val destPath = createImageFile(context).absolutePath
            LogUtils.d(TAG, "destPath====$destPath")
            compressBitmapToFile(bitmap, destPath)
            Handler(Looper.getMainLooper()).post { mOnImageSavedCallback?.onFinishCallback(destPath) }
            bitmap.recycle()
            System.gc()
        }.start()
    }

    interface OnImageSavedCallback {
        fun onFinishCallback(path: String?)
    }
}