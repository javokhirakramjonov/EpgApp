package co.proexe.model.repository

import co.proexe.R
import co.proexe.model.data.AccountInfo
import co.proexe.model.data.NavigationDrawerItem
import co.proexe.model.data.NavigationDrawerModel
import javax.inject.Inject

class DrawerRepository @Inject constructor() {

    private fun getUserInfo(): AccountInfo {
        return AccountInfo(USER_NAME, USER_ID)
    }

    fun getData(): NavigationDrawerModel {
        return NavigationDrawerModel(
            accountInfo = getUserInfo(),
            drawerItem = listOf(
                NavigationDrawerItem(R.string.drawer_main_page),
                NavigationDrawerItem(R.string.drawer_tv_programme, true),
                NavigationDrawerItem(R.string.drawer_channels),
                NavigationDrawerItem(R.string.drawer_favourites),
                NavigationDrawerItem(R.string.drawer_recordings),
                NavigationDrawerItem(R.string.drawer_movies),
                NavigationDrawerItem(R.string.drawer_series),
                NavigationDrawerItem(R.string.drawer_kids),
            )
        )
    }

    companion object {
        private const val USER_NAME = "Javahere"
        private const val USER_ID = 1234500L
    }
}