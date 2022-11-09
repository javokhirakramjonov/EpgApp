package co.proexe.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import co.proexe.R
import co.proexe.databinding.ScreenMainBinding
import co.proexe.model.data.DayTile
import co.proexe.model.data.TvProgramme
import co.proexe.ui.adapter.ChannelAdapter
import co.proexe.ui.adapter.TimeAdapter
import co.proexe.ui.viewModel.MainScreenViewModel
import co.proexe.ui.viewModel.impl.MainScreenViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val adapterChannel = ChannelAdapter()
    private val adapterTime = TimeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            rvChannel.adapter = adapterChannel
            rvChannel.layoutManager = LinearLayoutManager(requireContext())

            rvTime.adapter = adapterTime
            rvTime.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.timeLiveData.observe(viewLifecycleOwner, timesObserver)
        viewModel.programmesLiveData.observe(viewLifecycleOwner, programmesObserver)
    }

    private val timesObserver = Observer<List<DayTile>> {
        adapterTime.submitList(it)
    }

    private val programmesObserver = Observer<List<TvProgramme>> {
        adapterChannel.submitList(it)
    }

}