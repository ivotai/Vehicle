package com.unicorn.vehicle.ui

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.helper.UpdateHelper
import com.unicorn.vehicle.app.loggedUser
import com.unicorn.vehicle.ui.adapter.pager.MainPagerAdapter
import com.unicorn.vehicle.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_main.*

class MainAct : BaseAct() {

    override fun bindIntent() {
        UpdateHelper.checkUpdate(this)
    }

    override fun initViews() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = MainPagerAdapter.titles.size - 1
        addDrawer()

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                drawer.setSelection(position.toLong())
            }
        })
    }

    private fun addDrawer() {
        val accountHeader = AccountHeaderBuilder()
            .withActivity(this)
            .addProfiles(
                ProfileDrawerItem()
                    .withName(loggedUser.userName)
                    .withEmail(loggedUser.roleName)
            )
            .build()
        drawer = DrawerBuilder()
            .withActivity(this)
            .withAccountHeader(accountHeader)
            .addDrawerItems(
                PrimaryDrawerItem()
                    .withIdentifier(0)
                    .withIcon(FontAwesome.Icon.faw_clipboard)
                    .withName(MainPagerAdapter.titles[0]),
                PrimaryDrawerItem().withIdentifier(1)
                    .withIcon(FontAwesome.Icon.faw_car)
                    .withName(MainPagerAdapter.titles[1])
            )
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    viewPager.currentItem = drawerItem.identifier.toInt()
                    return false
                }
            })
            .build()
    }

    private lateinit var drawer: Drawer

    override val layoutId = R.layout.act_main

}
