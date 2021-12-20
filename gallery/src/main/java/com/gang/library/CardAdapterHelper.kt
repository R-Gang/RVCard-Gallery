package com.gang.library

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gang.library.common.utils.dip2px

/**
 * adapter中调用onCreateViewHolder, onBindViewHolder
 * Created by haoruigang on 2021-12-20
 */
class CardAdapterHelper {
    private var mPagePadding = 15
    private var mShowLeftCardWidth = 15
    fun onCreateViewHolder(parent: ViewGroup, itemView: View) {
        val lp: RecyclerView.LayoutParams = itemView.layoutParams as RecyclerView.LayoutParams
        lp.width = parent.width - dip2px(2 * (mPagePadding + mShowLeftCardWidth))
        itemView.layoutParams = lp
    }

    fun onBindViewHolder(itemView: View, position: Int, itemCount: Int) {
        val padding: Int = dip2px(mPagePadding)
        itemView.setPadding(padding, 0, padding, 0)
        val leftMarin = if (position == 0) padding + dip2px(mShowLeftCardWidth) else 0
        val rightMarin =
            if (position == itemCount - 1) padding + dip2px(mShowLeftCardWidth) else 0
        setViewMargin(itemView, leftMarin, 0, rightMarin, 0)
    }

    private fun setViewMargin(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
            view.layoutParams = lp
        }
    }

    fun setPagePadding(pagePadding: Int) {
        mPagePadding = pagePadding
    }

    fun setShowLeftCardWidth(showLeftCardWidth: Int) {
        mShowLeftCardWidth = showLeftCardWidth
    }
}