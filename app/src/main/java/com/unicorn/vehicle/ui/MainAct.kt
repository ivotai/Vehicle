package com.unicorn.vehicle.ui

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.Globals
import com.unicorn.vehicle.ui.adapter.pager.MainPagerAdapter
import com.unicorn.vehicle.ui.base.BaseAct
import kotlinx.android.synthetic.main.act_main.*

class MainAct : BaseAct() {

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

    private fun addDrawer() = with(Globals.loggedUser) {
        val accountHeader = AccountHeaderBuilder()
            .withActivity(this@MainAct)
//            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem().withName(userName)
                    .withEmail("角色")
//                    .withIcon(getResources().getDrawable(R.drawable.profile))
            )
            .withOnAccountHeaderListener(object : AccountHeader.OnAccountHeaderListener {
                override fun onProfileChanged(
                    view: View?,
                    profile: IProfile<*>,
                    current: Boolean
                ): Boolean {
                    return false
                }
            })
            .build()

        drawer = DrawerBuilder()
            .withActivity(this@MainAct)
            .withAccountHeader(accountHeader)
            .withTranslucentStatusBar(false)
            .addDrawerItems(
                PrimaryDrawerItem().withIcon(FontAwesome.Icon.faw_clipboard).withIdentifier(0).withName(
                    MainPagerAdapter.titles[0]
                ),
                PrimaryDrawerItem().withIcon(FontAwesome.Icon.faw_car).withIdentifier(1).withName(
                    MainPagerAdapter.titles[1]
                )
            )
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    viewPager.currentItem = drawerItem.identifier.toInt()
                    return true
                }
            })
            .build()
    }

    private lateinit var drawer: Drawer

    override val layoutId = R.layout.act_main

}
