package com.gang.scroll.content

import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.AbsListView
import android.widget.ListView
import com.gang.scroll.ScrollLayout

open class ContentListView : ListView {

    private val compositeScrollListener: CompositeScrollListener = CompositeScrollListener()
    private var showShadow = false
    private var shadowView: View? = null


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    /**
     * 添加一个OnScrollListener,不会取代已添加OnScrollListener
     *
     *
     * **Make sure call this on UI thread**
     *
     *
     * @param listener the listener to add
     */
    override fun setOnScrollListener(listener: OnScrollListener) {
        addOnScrollListener(listener)
    }

    /**
     * 添加一个OnScrollListener,不会取代已添加OnScrollListener
     *
     *
     * **Make sure call this on UI thread**
     *
     *
     * @param listener the listener to add
     */
    fun addOnScrollListener(listener: OnScrollListener?) {
        throwIfNotOnMainThread()
        compositeScrollListener.addOnScrollListener(listener)
    }

    /**
     * 删除前一个添加scrollListener,只会删除完全相同的对象
     *
     *
     * **Make sure call this on UI thread.**
     *
     *
     * @param listener the listener to remove
     */
    fun removeOnScrollListener(listener: OnScrollListener?) {
        throwIfNotOnMainThread()
        compositeScrollListener.removeOnScrollListener(listener)
    }

    /**
     * 需要调用之前setOnScrollListener
     *
     * @param shadowView the shadow view
     */
    fun setTopShadowView(shadowView: View?) {
        if (shadowView == null) {
            return
        }
        this.shadowView = shadowView
        addOnScrollListener(object : OnScrollListener {
            override fun onScroll(
                view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int,
                totalItemCount: Int
            ) {
                val firstChild = view.getChildAt(0)
                if (firstChild != null) {
                    if (firstVisibleItem == 0 && firstChild.top == 0) {
                        showShadow = false
                        showTopShadow()
                    } else if (!showShadow) {
                        showShadow = true
                        showTopShadow()
                    }
                }
            }

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
        })
    }

    private fun showTopShadow() {
        if (shadowView == null || shadowView?.visibility == VISIBLE) {
            return
        }
        shadowView?.visibility = VISIBLE
    }

    private fun hideTopShadow() {
        if (shadowView == null || shadowView?.visibility == GONE) {
            return
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var parent = parent
        while (parent != null) {
            if (parent is ScrollLayout) {
                parent.setAssociatedListView(this)
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

    private inner class CompositeScrollListener : OnScrollListener {
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

        fun removeOnScrollListener(listener: OnScrollListener?) {
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

        override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
            val listeners: List<OnScrollListener> = ArrayList(scrollListenerList)
            for (listener in listeners) {
                listener.onScrollStateChanged(view, scrollState)
            }
        }

        override fun onScroll(
            view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int,
            totalItemCount: Int
        ) {
            val listeners: List<OnScrollListener> = ArrayList(scrollListenerList)
            for (listener in listeners) {
                listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount)
            }
        }
    }

    init {
        super.setOnScrollListener(compositeScrollListener)
        viewTreeObserver.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            val layoutParams = layoutParams
            var parent = parent
            while (parent != null) {
                if (parent is ScrollLayout) {
                    val height =
                        (parent as ScrollLayout).measuredHeight - (parent as ScrollLayout).minOffset
                    if (layoutParams.height == height) {
                        return@OnGlobalLayoutListener
                    } else {
                        layoutParams.height = height
                        break
                    }
                }
                parent = parent.parent
            }
            setLayoutParams(layoutParams)
        })
    }

}