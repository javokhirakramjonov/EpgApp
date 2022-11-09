package co.proexe.ui.viewModel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.proexe.model.data.DayTile
import co.proexe.model.data.NavigationDrawerItem
import co.proexe.model.data.NavigationDrawerModel
import co.proexe.model.data.TvProgramme
import co.proexe.model.repository.DrawerRepository
import co.proexe.model.repository.LocalEpgRepository
import co.proexe.model.repository.TimeRepository
import co.proexe.ui.viewModel.MainScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(
    private val localEpgRepository: LocalEpgRepository,
    private val timeRepository: TimeRepository,
    private val drawerRepository: DrawerRepository
) : ViewModel(), MainScreenViewModel {

    override val drawerDataLiveData = MutableLiveData<NavigationDrawerModel>()
    override val timeLiveData = MutableLiveData<List<DayTile>>()
    override val programmesLiveData = MutableLiveData<List<TvProgramme>>()
    override val isDrawerOpenLiveData = MutableLiveData<Boolean>()
    override val selectedCategory = MutableLiveData<NavigationDrawerItem>()
    override val selectedProgram = MutableLiveData<TvProgramme>()


    init {
        loadDrawerData()
        loadTimes()
        loadProgrammes()
    }

    override fun loadDrawerData() {
        viewModelScope.launch(Dispatchers.IO) {
            drawerDataLiveData.postValue(drawerRepository.getData())
        }
    }

    override fun loadTimes() {
        viewModelScope.launch(Dispatchers.IO) {
            timeLiveData.postValue(timeRepository.getDayTiles())
        }
    }

    override fun loadProgrammes(time: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            programmesLiveData.postValue(
                localEpgRepository.getProgrammesForDateTime(
                    time,
                    100
                )
            )
        }
    }

    override fun openDrawer() {
        isDrawerOpenLiveData.value = true
    }

    override fun closeDrawer() {
        isDrawerOpenLiveData.value = false
    }

    override fun selectProgram(program: TvProgramme) {
        selectedProgram.value = program
    }

    override fun selectTime(time: DayTile) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time.timestamp
        loadProgrammes(calendar.time)
    }

    override fun selectProgramCategory(programCategory: NavigationDrawerItem) {
        selectedCategory.value = programCategory
        closeDrawer()
    }
}