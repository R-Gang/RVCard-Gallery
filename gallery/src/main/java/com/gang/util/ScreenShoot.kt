package com.gang.util

import android.app.Activity
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.util.LruCache
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * 作者：Created by haoruigang on 2018/8/20.
 * 描述：各种view截屏
 */
class ScreenShoot {

    companion object {
        private const val TAG = "ScreenShoot"

        /**
         * 获取指定View的截屏
         * @param view
         */
        fun viewSaveToImage(view: View): Bitmap {
            view.isDrawingCacheEnabled = true
            view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            view.drawingCacheBackgroundColor = Color.WHITE

            // 把一个View转换成图片
            val cachebmp: Bitmap = loadBitmapFromView(view)
            view.destroyDrawingCache()
            return cachebmp
        }

        fun loadBitmapFromView(v: View): Bitmap {
            val w = v.width
            val h = v.height
            val bmp: Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val c = Canvas(bmp)
            c.drawColor(Color.WHITE)
            /** 如果不设置canvas画布为白色，则生成透明  */
            v.layout(0, 0, w, h)
            v.draw(c)
            return bmp
        }

        /**
         * 获取指定Activity的截屏
         */
        fun activityScreenShoot(activity: Activity): Bitmap {

            // View是你需要截图的View
            val view: View = activity.getWindow().getDecorView()
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bitmap: Bitmap = view.drawingCache

            // 获取状态栏高度
            val frame = Rect()
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame)
            val statusBarHeight = frame.top
            println(statusBarHeight)

            // 获取屏幕长和高
            val width: Int = activity.getWindowManager().getDefaultDisplay().getWidth()
            val height: Int = activity.getWindowManager().getDefaultDisplay().getHeight()

            // 去掉标题栏
            val b: Bitmap =
                Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight)
            view.destroyDrawingCache()
            return b
        }

        /**
         * 获取scrollview的截屏
         */
        fun shotScrollView(scrollView: ScrollView): Bitmap? {
            var h = 0
            var bitmap: Bitmap? = null
            for (i in 0 until scrollView.getChildCount()) {
                h += scrollView.getChildAt(i).getHeight()
                scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"))
            }
            bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            scrollView.draw(canvas)
            return bitmap
        }

        /**
         * 获取listview的截屏
         *
         * @param listview
         * @return
         */
        fun shotListView(listview: ListView): Bitmap {
            val adapter = listview.adapter
            val itemscount = adapter.count
            var allitemsheight = 0
            val bmps: MutableList<Bitmap> = ArrayList<Bitmap>()

            //循环对listview的item进行截图， 最后拼接在一起
            for (i in 0 until itemscount) {
                val childView = adapter.getView(i, null, listview)
                childView.measure(
                    View.MeasureSpec.makeMeasureSpec(listview.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                childView.layout(0, 0, childView.measuredWidth, childView.measuredHeight)
                childView.isDrawingCacheEnabled = true
                childView.buildDrawingCache()
                bmps.add(childView.drawingCache)
                allitemsheight += childView.measuredHeight
                //这里可以把listview中单独的item进行保存
//            viewSaveToImage(childView.getDrawingCache());
            }
            val w = listview.measuredWidth
            val bigbitmap: Bitmap = Bitmap.createBitmap(w, allitemsheight, Bitmap.Config.ARGB_8888)
            val bigcanvas = Canvas(bigbitmap)
            val paint = Paint()
            var iHeight = 0
            for (i in bmps.indices) {
                var bmp: Bitmap? = bmps[i]
                bigcanvas.drawBitmap(bmp!!, 0f, iHeight.toFloat(), paint)
                iHeight += bmp.getHeight()
                bmp.recycle()
                bmp = null
            }
            return bigbitmap
        }

        /**
         * recycleview截图
         *
         * @param view
         * @return
         */
        fun shotRecyclerView(view: RecyclerView): Bitmap? {
            val adapter = view.adapter
            var bigBitmap: Bitmap? = null
            if (adapter != null) {
                val size: Int = adapter.getItemCount()
                var height = 0
                val paint = Paint()
                var iHeight = 0
                val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

                // Use 1/8th of the available memory for this memory cache.
                val cacheSize = maxMemory / 8
                val bitmaCache: LruCache<String, Bitmap> = LruCache<String, Bitmap>(cacheSize)
                for (i in 0 until size) {
                    val holder: RecyclerView.ViewHolder =
                        adapter.createViewHolder(view, adapter.getItemViewType(i))
                    adapter.onBindViewHolder(holder, i)
                    holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                    holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight())
                    holder.itemView.setDrawingCacheEnabled(true)
                    holder.itemView.buildDrawingCache()
                    val drawingCache: Bitmap = holder.itemView.getDrawingCache()
                    if (drawingCache != null) {
                        bitmaCache.put(i.toString(), drawingCache)
                    }
                    height += holder.itemView.getMeasuredHeight()
                }
                bigBitmap =
                    Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888)
                val bigCanvas = Canvas(bigBitmap)
                val lBackground: Drawable = view.getBackground()
                if (lBackground is ColorDrawable) {
                    val lColorDrawable: ColorDrawable = lBackground as ColorDrawable
                    val lColor: Int = lColorDrawable.getColor()
                    bigCanvas.drawColor(lColor)
                }
                for (i in 0 until size) {
                    val bitmap: Bitmap = bitmaCache[i.toString()]
                    bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                    iHeight += bitmap.getHeight()
                    bitmap.recycle()
                }
            }
            return bigBitmap
        }

        /**
         * 压缩bitmap
         *
         * @param bitmap
         * @return
         */
        fun compressBitmap(bitmap: Bitmap): Bitmap {
            val os = ByteArrayOutputStream() //创建一个字节数组输出流对象
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os) //通过bitmap中的compress,将图片压缩到os流对象中.
            //其中第二个参数quality,为100表示不压缩,如果为80,表示压缩百分之20.
            val bt = os.toByteArray() //将流对象转行成数组
            val bitmap1: Bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.size) //将字节数组转换成bitmap图片
            Log.d(TAG, "compressBitmap: 压缩后bitmap的大小：" + getBitmapSize(bitmap1))
            return bitmap1
        }

        /**
         * 得到bitmap的大小
         */
        fun getBitmapSize(bitmap: Bitmap): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
                return bitmap.getAllocationByteCount()
            }
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) { //API 12
                bitmap.getByteCount()
            } else bitmap.getRowBytes() * bitmap.getHeight()
            // 在低版本中用一行的字节x高度
            //earlier version
        }
    }
}