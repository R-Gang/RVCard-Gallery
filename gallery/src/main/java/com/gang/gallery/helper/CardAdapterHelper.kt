package com.gang.gallery.helper

import android.view.View
import android.view.ViewGroup
import com.gang.tools.kotlin.dimension.dp
import com.gang.tools.kotlin.view.widthValue

/**
 * adapter中调用onCreateViewHolder, onBindViewHolder
 * Created by haoruigang on 2021-12-20
 */
open class CardAdapterHelper {
    private var mPagePadding = 15.dp
    private var mShowLeftCardWidth = 15.dp
    fun onCreateViewHolder(parent: ViewGroup, itemView: View?) {
        itemView?.apply {
            widthValue = (parent.width - 2 * (mPagePadding + mShowLeftCardWidth))
        }
    }

    fun onBindViewHolder(itemView: View, position: Int, itemCount: Int) {
        val padding: Int = mPagePadding
        itemView.setPadding(padding, 0, padding, 0)
        val leftMarin: Int =
            if (position == 0) (padding + mShowLeftCardWidth) else 0
        val rightMarin: Int =
            if (position == itemCount - 1) (padding + mShowLeftCardWidth) else 0
        setViewMargin(itemView, leftMarin, 0, rightMarin, 0)
    }

    private fun setViewMargin(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
            view.layoutParams = lp
        }
    }

    /**
     * pagePadding (dp)
     */
    fun setPagePadding(pagePadding: Int) {
        mPagePadding = pagePadding
    }

    /**
     * showLeftCardWidth (dp)
     */
    fun setShowLeftCardWidth(showLeftCardWidth: Int) {
        mShowLeftCardWidth = showLeftCardWidth
    }
}