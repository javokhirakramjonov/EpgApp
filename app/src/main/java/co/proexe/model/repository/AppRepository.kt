package co.proexe.model.repository

import co.proexe.model.data.MenuModel
import javax.inject.Inject

class AppRepository @Inject constructor() {

    fun getMenuItems() : List<MenuModel> {
        return listOf(
            MenuModel(name = "Wszystkie"),
            MenuModel(name = "Ulubione"),
            MenuModel(name = "Dzieci"),
            MenuModel(name = "Edukacja"),
            MenuModel(name = "Filmy i seriale"),
            MenuModel(name = "Informacja"),
            MenuModel(name = "Muzyka"),
            MenuModel(name = "Ogólne"),
            MenuModel(name = "Sport"),
            MenuModel(name = "Styl Życia"),
        )
    }

}