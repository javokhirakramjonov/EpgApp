package co.proexe.ui.viewModel

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.LiveData
import co.proexe.model.data.DayTile
import co.proexe.model.data.NavigationDrawerItem
import co.proexe.model.data.NavigationDrawerModel
import co.proexe.model.data.TvProgramme
import java.util.*

interface MainScreenViewModel {
    val drawerDataLiveData: LiveData<NavigationDrawerModel>
    val timeLiveData: LiveData<List<DayTile>>
    val programmesLiveData: LiveData<List<TvProgramme>>
    val isDrawerOpenLiveData: LiveData<Boolean>
    val selectedCategory: LiveData<NavigationDrawerItem>
    val selectedProgram: LiveData<TvProgramme>
    val menuLiveData: LiveData<PopupMenu>
    val selectedMenuLiveData: LiveData<MenuItem>

    fun loadDrawerData()
    fun loadTimes()
    fun loadProgrammes(time: Date = Calendar.getInstance().time)

    fun openDrawer()
    fun closeDrawer()

    fun selectProgram(program: TvProgramme)
    fun selectTime(time: DayTile)
    fun selectProgramCategory(programCategory: NavigationDrawerItem)

    fun loadMenu(view: View)
    fun selectMenu(item: MenuItem)
}