package com.clevmania.velatest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.clevmania.velatest.data.repository.VelaRepository

class VelaViewModelFactory(private val velaRepository: VelaRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VelaViewModel(velaRepository) as T
    }
}