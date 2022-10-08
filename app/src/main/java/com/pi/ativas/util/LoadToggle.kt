package com.pi.ativas.util

import android.app.Activity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.pi.ativas.R

class LoadToggle {
    companion object {

        fun loadMenu(
            drawerLayout: DrawerLayout,
            activity: Activity,
            toolbar: Toolbar
        ): ActionBarDrawerToggle {

            val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
                activity,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            return toggle
        }
    }

}