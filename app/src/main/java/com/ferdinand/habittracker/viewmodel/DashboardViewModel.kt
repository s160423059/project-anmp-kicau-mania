package com.ferdinand.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdinand.habittracker.model.Habit

class DashboardViewModel : ViewModel() {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()

    private val habits = arrayListOf<Habit>()
    private var nextId = 1

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false

        habitsLD.value = ArrayList(habits)

        loadingLD.value = false
    }

    fun addHabit(
        name: String,
        description: String,
        goal: Int,
        unit: String,
        iconName: String
    ) {
        val newHabit = Habit(
            id = nextId,
            name = name,
            description = description,
            goal = goal,
            unit = unit,
            currentProgress = 0,
            iconName = iconName
        )

        nextId += 1
        habits.add(newHabit)

        habitsLD.value = ArrayList(habits)
    }

    fun increaseProgress(habit: Habit) {
        val selectedHabit = habits.find { it.id == habit.id }

        if (selectedHabit != null && selectedHabit.currentProgress < selectedHabit.goal) {
            selectedHabit.currentProgress += 1
        }

        habitsLD.value = ArrayList(habits)
    }

    fun decreaseProgress(habit: Habit) {
        val selectedHabit = habits.find { it.id == habit.id }

        if (selectedHabit != null && selectedHabit.currentProgress > 0) {
            selectedHabit.currentProgress -= 1
        }

        habitsLD.value = ArrayList(habits)
    }
}