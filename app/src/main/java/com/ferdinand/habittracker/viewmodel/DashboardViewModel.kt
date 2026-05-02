package com.ferdinand.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdinand.habittracker.model.Habit

class DashboardViewModel : ViewModel() {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false

        habitsLD.value = arrayListOf()

        loadingLD.value = false
    }
}