package com.unicorn.vehicle.ui

import android.view.View
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.unicorn.vehicle.R
import com.unicorn.vehicle.ui.adapter.pager.MainPagerAdapter
import com.unicorn.vehicle.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_main.*

class MainAct : BaseAct() {

    override fun initViews() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = MainPagerAdapter.titles.size - 1
        addDrawer()
    }

    private fun addDrawer() {
        val item1 = PrimaryDrawerItem().withIdentifier(1).withName("用车申请")

        val result = DrawerBuilder()
            .withActivity(this)
            .addDrawerItems(
                item1,
                item1
            )
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    // do something with the clicked item :D
                    return false
                }
            })
            .build()
    }

    override val layoutId = R.layout.act_main

}
