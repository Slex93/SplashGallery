package com.stslex.splashgallery.ui.detail_photo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.stslex.splashgallery.R
import com.stslex.splashgallery.databinding.FragmentPhotoDetailsBinding
import com.stslex.splashgallery.utils.Result
import com.stslex.splashgallery.utils.SetImageWithGlide
import com.stslex.splashgallery.utils.base.BaseFragment
import com.stslex.splashgallery.utils.setImageWithRequest
import com.stslex.splashgallery.utils.startDownload
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PhotoDetailsFragment : BaseFragment() {

    private var _binding: FragmentPhotoDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotoDetailsViewModel by viewModels { viewModelFactory.get() }

    private lateinit var id: String

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
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNavigationArgs()
        setToolbar()
        setListener()
        binding.imageImageView.setOnClickListener { image ->
            val directions =
                PhotoDetailsFragmentDirections.actionNavSinglePhotoToNavSingleImage(
                    url = image.transitionName
                )
            val extras = FragmentNavigatorExtras(image to image.transitionName)
            findNavController().navigate(directions, extras)
        }

        binding.userCardView.setOnClickListener { card ->
            val directions =
                PhotoDetailsFragmentDirections.actionNavSinglePhotoToNavUser(card.transitionName)
            val extras = FragmentNavigatorExtras(card to card.transitionName)
            findNavController().navigate(directions, extras)
        }
    }

    private fun setListener() = viewLifecycleOwner.lifecycleScope.launch {
        viewModel.getCurrentPhoto(id).collect {
            when (it) {
                is PhotoUIResult.Success -> {
                    val item = it.data
                    with(binding) {
                        item.bindDetailPhoto(
                            glide = setImageWithGlide,
                            image = imageImageView,
                            avatar = avatarImageView,
                            username = usernameTextView,
                            userCardView = userCardView,
                            photoAperture = apertureTextView,
                            photoCamera = cameraTextView,
                            photoDimension = dimensionTextView,
                            photoFocal = focalTextView,
                        )
                    }

                    binding.singlePhotoDownload.setOnClickListener {
                        item.downloadPhoto { id ->
                            downloadPhoto(id)
                        }
                    }
                }
                is PhotoUIResult.Failure -> {
                }
                is PhotoUIResult.Loading -> {
                }
            }
        }
    }


    private fun downloadPhoto(id: String) = viewLifecycleOwner.lifecycleScope.launch {
        viewModel.downloadPhoto(id).collect {
            when (it) {
                is Result.Success -> {
                    this.launch {
                        startDownload(it.data.url, id)
                    }
                }
                is Result.Failure -> {

                }
                is Result.Loading -> {

                }
            }
        }
    }

    private val setImageWithGlide = SetImageWithGlide { url, imageView, needCrop, needCircleCrop ->
        setImageWithRequest(url, imageView, needCrop, needCircleCrop)
    }

    private fun getNavigationArgs() {
        postponeEnterTransition()
        val extras: PhotoDetailsFragmentArgs by navArgs()
        id = extras.id
        val url = extras.url
        binding.imageImageView.transitionName = url
        setImageWithRequest(url, binding.imageImageView, needCrop = true)
    }

    private fun setToolbar() {
        (requireActivity() as AppCompatActivity).run {
            setSupportActionBar(binding.singlePhotoToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}