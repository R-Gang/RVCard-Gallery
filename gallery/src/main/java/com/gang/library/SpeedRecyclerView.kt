package com.gang.library

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView

/**
 * 控制fling速度的RecyclerView
 *
 * Created by haoruigang on 2021-12-20.
 */
class SpeedRecyclerView : RecyclerView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyle: Int) : super(context,
        attrs,
        defStyle) {
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        var velocityX = velocityX
        var velocityY = velocityY
        velocityX = solveVelocity(velocityX)
        velocityY = solveVelocity(velocityY)
        return super.fling(velocityX, velocityY)
    }

    private fun solveVelocity(velocity: Int): Int {
        return if (velocity > 0) {
            velocity.coerceAtMost(FLING_MAX_VELOCITY)
        } else {
            velocity.coerceAtLeast(-FLING_MAX_VELOCITY)
        }
    }

    companion object {
        private const val FLING_SCALE_DOWN_FACTOR = 0.5f // 减速因子
        private const val FLING_MAX_VELOCITY = 8000 // 最大顺时滑动速度
    }
}