package co.proexe.ui.screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import co.proexe.R
import co.proexe.databinding.ScreenDrawerBinding
import co.proexe.model.data.DayTile
import co.proexe.model.data.NavigationDrawerItem
import co.proexe.model.data.NavigationDrawerModel
import co.proexe.model.data.TvProgramme
import co.proexe.ui.adapter.ChannelAdapter
import co.proexe.ui.adapter.DrawerAdapter
import co.proexe.ui.adapter.TimeAdapter
import co.proexe.ui.viewModel.MainScreenViewModel
import co.proexe.ui.viewModel.impl.MainScreenViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_drawer) {

    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val binding by viewBinding(ScreenDrawerBinding::bind)
    private val adapterChannel = ChannelAdapter()
    private val adapterTime = TimeAdapter()
    private val adapterDrawer = DrawerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.selectedProgram.observe(this, selectProgramObserver)
        viewModel.selectedCategory.observe(this, selectCategoryObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            mainScreen.apply {
                adapterChannel.setSelector {
                    viewModel.selectProgram(it)
                }
                rvChannel.adapter = adapterChannel
                rvChannel.layoutManager = LinearLayoutManager(requireContext())

                adapterTime.setSelector {
                    viewModel.selectTime(it)
                }
                rvTime.adapter = adapterTime
                rvTime.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                buttonDrawer.setOnClickListener {
                    viewModel.openDrawer()
                }
            }

            adapterDrawer.setSelector {
                viewModel.selectProgramCategory(it)
            }
            rvProgrammes.adapter = adapterDrawer
            rvProgrammes.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.drawerDataLiveData.observe(viewLifecycleOwner, drawerDataObserver)
        viewModel.isDrawerOpenLiveData.observe(viewLifecycleOwner, drawerObserver)
        viewModel.timeLiveData.observe(viewLifecycleOwner, timesObserver)
        viewModel.programmesLiveData.observe(viewLifecycleOwner, programmesObserver)
    }

    private val selectProgramObserver = Observer<TvProgramme> {
        Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
    }

    private val selectCategoryObserver = Observer<NavigationDrawerItem> {
        Toast.makeText(requireContext(), getString(it.labelId), Toast.LENGTH_SHORT).show()
    }

    private val drawerDataObserver = Observer<NavigationDrawerModel> {
        binding.apply {
            username.text = it.accountInfo.userName
            userId.text = "${it.accountInfo.userAccountValue}"
            adapterDrawer.submitList(it.drawerItem)
        }
    }

    private val drawerObserver = Observer<Boolean> {
        if (it) {
            binding.drawerScreen.openDrawer(GravityCompat.START)
        } else {
            binding.drawerScreen.closeDrawer(GravityCompat.START)
        }
    }

    private val timesObserver = Observer<List<DayTile>> {
        adapterTime.submitList(it)
    }

    private val programmesObserver = Observer<List<TvProgramme>> {
        adapterChannel.submitList(it)
    }

}