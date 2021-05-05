package com.fittingvroom.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment<T> : Fragment() {

    abstract val model : BaseViewModel<T>

    protected abstract fun renderData(state: T)
}