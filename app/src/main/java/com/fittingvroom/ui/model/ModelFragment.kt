package com.fittingvroom.ui.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fittingvroom.R

class ModelFragment : Fragment() {

    private lateinit var modelViewModel: ModelViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        modelViewModel =
                ViewModelProvider(this).get(ModelViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_model, container, false)
        val textView: TextView = root.findViewById(R.id.text_model)
        modelViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}