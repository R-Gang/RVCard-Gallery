package com.gang.gallery.helper

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gang.tools.kotlin.dimension.dip2px
import com.gang.tools.kotlin.utils.LogUtils
import kotlin.math.abs

/**
 * Created by haoruigang on 2021-12-20.
 */
class CardScaleHelper {
    private var mRecyclerView: RecyclerView? = null
    private var mContext: Context? = null
    var mScale = 0.9f // 两边视图scale
    var mPagePadding = 15 // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
    var mShowLeftCardWidth = 15 // 左边卡片显示大小
    var mCardWidth = 0 // 卡片宽度
    var mOnePageWidth = 0 // 滑动一页的距离
    var mCardGalleryWidth = 0
    var currentItemPos = 0
    var mCurrentItemOffset = 0
    val mLinearSnapHelper = CardLinearSnapHelper()
    fun attachToRecyclerView(mRecyclerView: RecyclerView) {
        this.mRecyclerView = mRecyclerView
        mContext = mRecyclerView.context
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLinearSnapHelper.mNoNeedToScroll =
                        mCurrentItemOffset == 0 || mCurrentItemOffset == getDestItemOffset(
                            mRecyclerView.adapter?.itemCount as Int - 1
                        )
                } else {
                    mLinearSnapHelper.mNoNeedToScroll = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑
                if (dx != 0) { //去掉奇怪的内存疯涨问题
                    mCurrentItemOffset += dx
                    computeCurrentItemPos()
                    mContext?.apply {
                        LogUtils.i(
                            this,
                            String.format(
                                "dx=%s, dy=%s, mScrolledX=%s",
                                dx,
                                dy,
                                mCurrentItemOffset
                            )
                        )
                        onScrolledChangedCallback()
                    }
                }
            }
        })
        initWidth()
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView)
    }

    /**
     * 初始化卡片宽度
     */
    private fun initWidth() {
        mRecyclerView?.post(object : Runnable {
            override fun run() {
                mCardGalleryWidth = mRecyclerView?.width as Int
                mCardWidth = (mCardGalleryWidth - dip2px(2 * (mPagePadding + mShowLeftCardWidth).toFloat())).toInt()
                mOnePageWidth = mCardWidth
                mRecyclerView?.smoothScrollToPosition(currentItemPos)
                onScrolledChangedCallback()
            }
        })
    }

    fun getDestItemOffset(destPos: Int): Int {
        return mOnePageWidth * destPos
    }

    /**
     * 计算mCurrentItemOffset
     */
    fun computeCurrentItemPos() {
        if (mOnePageWidth <= 0) return
        var pageChanged = false
        // 滑动超过一页说明已翻页
        if (abs(mCurrentItemOffset - currentItemPos * mOnePageWidth) >= mOnePageWidth) {
            pageChanged = true
        }
        if (pageChanged) {
            val tempPos = currentItemPos
            currentItemPos = mCurrentItemOffset / mOnePageWidth
            mContext?.apply {
                LogUtils.d(
                    this,
                    String.format(
                        "=======onCurrentItemPos Changed======= tempPos=%s, mCurrentItemPos=%s",
                        tempPos,
                        currentItemPos
                    )
                )
            }
        }
    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    fun onScrolledChangedCallback() {
        val offset = mCurrentItemOffset - currentItemPos * mOnePageWidth
        val percent = Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001)
            .toFloat()
        mContext?.apply {
            LogUtils.d(this, String.format("offset=%s, percent=%s", offset, percent))
        }
        var leftView: View? = null
        var rightView: View? = null
        if (currentItemPos > 0) {
            leftView = mRecyclerView?.layoutManager?.findViewByPosition(currentItemPos - 1)
        }
        val currentView: View? = mRecyclerView?.layoutManager?.findViewByPosition(currentItemPos)
        if (currentItemPos < mRecyclerView?.adapter?.itemCount!! - 1) {
            rightView = mRecyclerView?.layoutManager?.findViewByPosition(currentItemPos + 1)
        }
        if (leftView != null) {
            // y = (1 - mScale)x + mScale
            leftView.scaleY = (1 - mScale) * percent + mScale
        }
        if (currentView != null) {
            // y = (mScale - 1)x + 1
            currentView.scaleY = (mScale - 1) * percent + 1
        }
        if (rightView != null) {
            // y = (1 - mScale)x + mScale
            rightView.scaleY = (1 - mScale) * percent + mScale
        }
    }

    fun setScale(scale: Float) {
        mScale = scale
    }

    fun setPagePadding(pagePadding: Int) {
        mPagePadding = pagePadding
    }

    fun setShowLeftCardWidth(showLeftCardWidth: Int) {
        mShowLeftCardWidth = showLeftCardWidth
    }
}