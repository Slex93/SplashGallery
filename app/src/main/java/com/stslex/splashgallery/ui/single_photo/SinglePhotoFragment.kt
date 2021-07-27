package com.stslex.splashgallery.ui.single_photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.stslex.splashgallery.databinding.FragmentSinglePhotoBinding
import com.stslex.splashgallery.utils.setImageWithRequest

class SinglePhotoFragment : Fragment() {

    private var _binding: FragmentSinglePhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSinglePhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val extras: SinglePhotoFragmentArgs by navArgs()
        val imageModel = extras.imageModel
        val transitionName = extras.transitionName
        binding.singlePhotoImage.transitionName = transitionName
        setImageWithRequest(url = imageModel.urls.regular, binding.singlePhotoImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}