package com.unicorn.vehicle.ui.chart

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ConvertUtils
import com.unicorn.vehicle.R
import com.unicorn.vehicle.ui.adapter.pager.CarChartPagerAdapter
import com.unicorn.vehicle.ui.base.BaseFra
import kotlinx.android.synthetic.main.fra_chart_car.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView

class CarChartFra : BaseFra() {

    override fun initViews() {
        viewPaper.offscreenPageLimit = CarChartPagerAdapter.titles.size - 1
        viewPaper.adapter = CarChartPagerAdapter(childFragmentManager)

        magicIndicator.setBackgroundResource(R.drawable.round_indicator_bg)
        val commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return CarChartPagerAdapter.titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = CarChartPagerAdapter.titles[index]
                clipPagerTitleView.textColor = mdColor
                clipPagerTitleView.textSize = ConvertUtils.dp2px(14f).toFloat()
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener {
//                    mFragmentContainerHelper.handlePageSelected(index)
//                    switchPages(index)
                    viewPaper.currentItem = index
                }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight = ConvertUtils.dp2px(36f)
                val borderWidth = UIUtil.dip2px(context, 1.0).toFloat()
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.lineHeight = lineHeight
                indicator.roundRadius = lineHeight / 2
                indicator.yOffset = borderWidth
                indicator.setColors(mdColor)
                return indicator
            }
        }
        magicIndicator.navigator = commonNavigator

        ViewPagerHelper.bind(magicIndicator, viewPaper)
    }

    private val mdColor by lazy { ContextCompat.getColor(context!!, R.color.md_indigo_300) }

    override val layoutId = R.layout.fra_chart_car

}