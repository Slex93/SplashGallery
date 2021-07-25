package com.stslex.wallpape.ui.main_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.stslex.splashgallery.R
import com.stslex.splashgallery.databinding.FragmentMainBinding
import com.stslex.splashgallery.ui.base.BaseFragment
import com.stslex.splashgallery.ui.main_screen.MainViewModel
import com.stslex.splashgallery.ui.main_screen_pager.PagerSharedViewModel
import com.stslex.splashgallery.utils.Result
import com.stslex.splashgallery.utils.appComponent


class MainFragment : BaseFragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels { viewModelFactory.get() }

    private val sharedViewModel: PagerSharedViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.applicationContext.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModelListener()
        initPager()
        initPagerSwitcher()
    }

    private fun initPagerSwitcher() {
        binding.mainViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        sharedViewModel.setIsAllImages(true)
                    }
                    1 -> {
                        sharedViewModel.setIsAllImages(false)
                    }
                }
            }
        })
    }

    private fun setViewModelListener() {
        sharedViewModel.pageNumberAllPhotos.observe(viewLifecycleOwner) {
            viewModel.getAllPhotos(it)
        }
        sharedViewModel.pageNumberCollections.observe(viewLifecycleOwner) {
            viewModel.getAllCollections(it)
        }
        viewModel.allPhotos.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    sharedViewModel.setPage(it.data)
                    binding.mainFragmentProgressBar.visibility = View.GONE
                }
                is Result.Failure -> {
                    Snackbar.make(binding.root, it.exception, Snackbar.LENGTH_SHORT).show()
                    binding.mainFragmentProgressBar.visibility = View.GONE
                }
                is Result.Loading -> {
                    binding.mainFragmentProgressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.allCollections.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    sharedViewModel.setCollection(it.data)
                    binding.mainFragmentProgressBar.visibility = View.GONE
                }
                is Result.Failure -> {
                    Snackbar.make(binding.root, it.exception, Snackbar.LENGTH_SHORT).show()
                    binding.mainFragmentProgressBar.visibility = View.GONE
                }
                is Result.Loading -> {
                    binding.mainFragmentProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initPager() {
        binding.mainViewPager.adapter = MainFragmentAdapter(this)
        val listOfTabs = mapOf(
            0 to getString(R.string.label_tab_layout_all),
            1 to getString(R.string.label_tab_layout_collection)
        )
        TabLayoutMediator(
            binding.mainTabLayout,
            binding.mainViewPager
        ) { tab, position ->
            tab.text = listOfTabs[position]
            binding.mainViewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}