package com.stslex.splashgallery.ui.single_collection

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.stslex.splashgallery.R
import com.stslex.splashgallery.databinding.FragmentSingleCollectionBinding
import com.stslex.splashgallery.ui.core.BaseFragment
import com.stslex.splashgallery.ui.user.UserSharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SingleCollectionFragment : BaseFragment() {

    private var _binding: FragmentSingleCollectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var titleExtra: String
    private val sharedViewModel: UserSharedViewModel by activityViewModels()

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
        _binding = FragmentSingleCollectionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNavigationArgs()
        setToolbar()
    }

    private fun setToolbar() {
        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(binding.mainToolbar)
        activity.supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            this.title = titleExtra
        }
    }

    private fun getNavigationArgs() {
        val extras: SingleCollectionFragmentArgs by navArgs()
        binding.mainToolbar.transitionName = extras.transitionName
        titleExtra = extras.title
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.setId(extras.transitionName)
        }.cancel(cause = null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}