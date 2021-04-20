package com.fittingvroom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fittingvroom.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding?.root
        init()
        return view
    }

    private fun init() {
        val bind = binding ?: return
        bind.homeBottomButton.setOnClickListener {
            Toast.makeText(requireContext(), "Добавить/изменить параметры", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
