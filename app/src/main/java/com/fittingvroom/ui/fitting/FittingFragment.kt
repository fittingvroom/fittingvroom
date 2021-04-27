package com.fittingvroom.ui.fitting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fittingvroom.databinding.FragmentFittingBinding
import com.fittingvroom.ui.view3d.SceneViewer


class FittingFragment : Fragment() {

    private var binding: FragmentFittingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFittingBinding.inflate(inflater, container, false)
        val view = binding?.root
        SceneViewer.showScene(context, resources, binding?.fittingSceneView, binding?.fittingPb)
        return view
    }

    override fun onResume() {
        super.onResume()
        binding?.fittingSceneView?.resume()
    }


    override fun onPause() {
        super.onPause()
        binding?.fittingSceneView?.pause()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}