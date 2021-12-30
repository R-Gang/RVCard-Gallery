package com.gang.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object BitmapUtils {
    var MIN_WIDTH = 100

    /**
     * 按最大边按一定大小缩放图片
     *
     * @param resources
     * @param resId
     * @param maxSize 压缩后最大长度
     * @return
     */
    fun scaleImage(resources: Resources?, resId: Int, maxSize: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)
        options.inSampleSize = calculateInSampleSize(options, maxSize, maxSize)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, resId, options)
    }

    /**
     * 计算inSampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        var reqWidth = reqWidth
        var reqHeight = reqHeight
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        return if (width < MIN_WIDTH) {
            inSampleSize
        } else {
            var heightRatio: Int
            if (width > height && reqWidth < reqHeight || width < height && reqWidth > reqHeight) {
                heightRatio = reqWidth
                reqWidth = reqHeight
                reqHeight = heightRatio
            }
            if (height > reqHeight || width > reqWidth) {
                heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) widthRatio else heightRatio
            }
            inSampleSize
        }
    }
}