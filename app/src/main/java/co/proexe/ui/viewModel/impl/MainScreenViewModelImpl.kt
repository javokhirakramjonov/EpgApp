package co.proexe.ui.viewModel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.proexe.model.data.DayTile
import co.proexe.model.data.TvProgramme
import co.proexe.model.repository.LocalEpgRepository
import co.proexe.model.repository.TimeRepository
import co.proexe.ui.viewModel.MainScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(
    private val localEpgRepository: LocalEpgRepository,
    private val timeRepository: TimeRepository,
) : ViewModel(), MainScreenViewModel {

    override val timeLiveData = MutableLiveData<List<DayTile>>()
    override val programmesLiveData = MutableLiveData<List<TvProgramme>>()

    init {
        loadTimes()
        loadProgrammes()
    }

    override fun loadTimes() {
        viewModelScope.launch(Dispatchers.IO) {
            timeLiveData.postValue(timeRepository.getDayTiles())
        }
    }

    override fun loadProgrammes() {
        viewModelScope.launch(Dispatchers.IO) {
            programmesLiveData.postValue(localEpgRepository.getProgrammesForDateTime(Calendar.getInstance().time, 100))
        }
    }
}