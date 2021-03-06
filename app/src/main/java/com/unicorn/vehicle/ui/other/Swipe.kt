package com.unicorn.vehicle.ui.other

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.jakewharton.rxbinding3.view.clicks
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.toIconicsColor
import com.mikepenz.iconics.utils.toIconicsSizeDp
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.displayDateFormat
import com.unicorn.vehicle.data.model.param.StatisticCommonParam
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.swipe.view.*
import org.joda.time.DateTime

class Swipe(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs),
    LayoutContainer {

    override val containerView = this

    private val list = listOf("近一年", "近三月", "近一月", "近一周", "近三天", "今天")
    private var pos = 2

    init {
        LayoutInflater.from(context).inflate(R.layout.swipe, this, true)
        refreshText()

        iivLeft.clicks().subscribe { left() }
        iivRight.clicks().subscribe { right() }
    }

    private fun right() {
        if (pos == list.size - 1) return
        pos++
        refreshText()
        swipeListener?.onSwipe(getParam())
    }

    private fun left() {
        if (pos == 0) return
        pos--
        refreshText()
        swipeListener?.onSwipe(getParam())
    }

    private fun getParam() = when (pos) {
        5 -> StatisticCommonParam(dateStart = DateTime().toString(displayDateFormat))
        4 -> StatisticCommonParam(dateStart = DateTime().minusDays(3).toString(displayDateFormat))
        3 -> StatisticCommonParam(dateStart = DateTime().minusWeeks(1).toString(displayDateFormat))
        2 -> StatisticCommonParam(dateStart = DateTime().minusMonths(1).toString(displayDateFormat))
        1 -> StatisticCommonParam(dateStart = DateTime().minusMonths(3).toString(displayDateFormat))
        0 -> StatisticCommonParam(dateStart = DateTime().minusYears(1).toString(displayDateFormat))
        else -> StatisticCommonParam()
    }

    private fun refreshText() {
        textView.text = list[pos]
    }

    fun setColor(color: Int){
        textView.setTextColor(color)
        iivLeft.setImageDrawable(IconicsDrawable(context)
            .icon(FontAwesome.Icon.faw_chevron_left)
            .color(color.toIconicsColor())
            .size(18.toIconicsSizeDp()))
        iivRight.setImageDrawable(IconicsDrawable(context)
            .icon(FontAwesome.Icon.faw_chevron_right)
            .color(color.toIconicsColor())
            .size(18.toIconicsSizeDp()))
    }

    interface SwipeListener {
        fun onSwipe(statisticCommonParam: StatisticCommonParam)
    }

    var swipeListener: SwipeListener? = null

}