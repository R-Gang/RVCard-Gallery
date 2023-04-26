package com.gang.scroll.content

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import com.gang.scroll.ScrollLayout

open class ContentScrollView : ScrollView {

    interface OnScrollChangedListener {
        fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)
    }

    private var listener: OnScrollChangedListener? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    fun setOnScrollChangeListener(listener: OnScrollChangedListener?) {
        this.listener = listener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        listener?.onScrollChanged(l, t, oldl, oldt)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var parent = this.parent
        while (parent != null) {
            if (parent is ScrollLayout) {
                parent.setAssociatedScrollView(this)
                break
            }
            parent = parent.parent
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val parent = this.parent
        if (parent is ScrollLayout) {
            if (parent.currentStatus === ScrollLayout.Status.OPENED) return false
        }
        return super.onTouchEvent(ev)
    }

}