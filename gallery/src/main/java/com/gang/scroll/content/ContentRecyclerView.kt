package com.gang.scroll.content

import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import com.gang.scroll.ScrollLayout

open class ContentRecyclerView : RecyclerView {

    private val compositeScrollListener: CompositeScrollListener = CompositeScrollListener()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    )
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context, attrs, defStyle
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var parent = parent
        while (parent != null) {
            if (parent is ScrollLayout) {
                parent.setAssociatedRecyclerView(this)
                break
            }
            parent = parent.parent
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    private fun throwIfNotOnMainThread() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "Must be invoked from the main thread." }
    }

    private inner class CompositeScrollListener : OnScrollListener() {
        private val scrollListenerList: MutableList<OnScrollListener> = ArrayList()
        fun addOnScrollListener(listener: OnScrollListener?) {
            if (listener == null) {
                return
            }
            for (scrollListener in scrollListenerList) {
                if (listener === scrollListener) {
                    return
                }
            }
            scrollListenerList.add(listener)
        }

        fun removeOnScrollListener(listener: AbsListView.OnScrollListener?) {
            if (listener == null) {
                return
            }
            val iterator = scrollListenerList.iterator()
            while (iterator.hasNext()) {
                val scrollListener = iterator.next()
                if (listener === scrollListener) {
                    iterator.remove()
                    return
                }
            }
        }

        override fun onScrollStateChanged(view: RecyclerView, scrollState: Int) {
            val listeners: List<OnScrollListener> = ArrayList(scrollListenerList)
            for (listener in listeners) {
                listener.onScrollStateChanged(view, scrollState)
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val listeners: List<OnScrollListener> = ArrayList(scrollListenerList)
            for (listener in listeners) {
                listener.onScrolled(recyclerView, dx, dy)
            }
        }
    }

    init {
        super.addOnScrollListener(compositeScrollListener)
        viewTreeObserver.addOnGlobalLayoutListener {
            val layoutParams = layoutParams
            var parent = parent
            while (parent != null) {
                if (parent is ScrollLayout) {
                    val height =
                        (parent as ScrollLayout).measuredHeight - (parent as ScrollLayout).minOffset
                    if (layoutParams.height == height) {
                        return@addOnGlobalLayoutListener
                    } else {
                        layoutParams.height = height
                        break
                    }
                }
                parent = parent?.parent
            }
            setLayoutParams(layoutParams)
        }
    }

}