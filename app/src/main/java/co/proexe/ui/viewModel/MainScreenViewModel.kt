package co.proexe.ui.viewModel

import androidx.lifecycle.LiveData
import co.proexe.model.data.DayTile
import co.proexe.model.data.TvProgramme

interface MainScreenViewModel {
    val timeLiveData: LiveData<List<DayTile>>
    val programmesLiveData: LiveData<List<TvProgramme>>

    fun loadTimes()
    fun loadProgrammes()
}