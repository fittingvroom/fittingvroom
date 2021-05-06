package com.fittingvroom.ui.pick_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.databinding.FragmentModelBinding

import com.fittingvroom.databinding.FragmentPickUpBinding
import com.google.android.material.tabs.TabLayoutMediator

class PickUpFragment:Fragment() {
    private var viewBinding: FragmentPickUpBinding? = null
    private val navigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPickUpBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}