package com.unicorn.vehicle.ui.other

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.unicorn.vehicle.R
import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import kotlinx.android.extensions.LayoutContainer

class Swipe(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs),
    LayoutContainer {

    override val containerView = this

    private val list = listOf("今天", "近三天", "本周", "本月")

    init {
        LayoutInflater.from(context).inflate(R.layout.swipe, this, true)
    }



    interface SwipeListener {
        fun onSwipe(statisticCommonParam: StatisticCommonParam)
    }

    lateinit var swipeListener: SwipeListener
}