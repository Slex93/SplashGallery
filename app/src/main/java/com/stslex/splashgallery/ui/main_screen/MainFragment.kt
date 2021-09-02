package com.stslex.splashgallery.ui.main_screen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialContainerTransform
import com.stslex.splashgallery.R
import com.stslex.splashgallery.databinding.FragmentMainBinding
import com.stslex.splashgallery.utils.Result
import com.stslex.splashgallery.utils.base.BaseFragment

class MainFragment : BaseFragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels { viewModelFactory.get() }
    private val sharedCollectionViewModel: MainSharedCollectionsViewModel by activityViewModels()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = getString(R.integer.transition_duration).toLong()
            scrimColor = Color.TRANSPARENT
        }
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
    }

    private fun setViewModelListener() {
        sharedCollectionViewModel.numberCollections.observe(viewLifecycleOwner) {
            viewModel.getAllCollections(it)
        }

        viewModel.allCollections.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    sharedCollectionViewModel.setCollection(it.data)
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
            1 to getString(R.string.label_collections)
        )
        postponeEnterTransition()
        binding.mainViewPager.doOnPreDraw {
            startPostponedEnterTransition()
        }
        TabLayoutMediator(
            binding.mainTabLayout,
            binding.mainViewPager
        ) { tab, position ->
            tab.text = listOfTabs[position]
            binding.mainViewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}