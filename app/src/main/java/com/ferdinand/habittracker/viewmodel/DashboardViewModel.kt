package com.ferdinand.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferdinand.habittracker.model.Habit

class DashboardViewModel : ViewModel() {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()

    private val habits = arrayListOf<Habit>()

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false

        // Data dummy sementara untuk mengetes RecyclerView.
        // Nanti di tahap Create Habit dan Persistence, bagian ini akan diganti.
        if (habits.isEmpty()) {
            habits.add(
                Habit(
                    id = 1,
                    name = "Drink Water",
                    description = "Stay hydrated throughout the day",
                    goal = 8,
                    unit = "glasses",
                    currentProgress = 3,
                    iconName = "Water"
                )
            )

            habits.add(
                Habit(
                    id = 2,
                    name = "Exercise",
                    description = "Daily workout routine",
                    goal = 30,
                    unit = "minutes",
                    currentProgress = 15,
                    iconName = "Exercise"
                )
            )

            habits.add(
                Habit(
                    id = 3,
                    name = "Read Books",
                    description = "Expand your knowledge",
                    goal = 20,
                    unit = "pages",
                    currentProgress = 20,
                    iconName = "Book"
                )
            )
        }

        habitsLD.value = ArrayList(habits)

        loadingLD.value = false
    }

    fun increaseProgress(habit: Habit) {
        if (habit.currentProgress < habit.goal) {
            habit.currentProgress += 1
        }

        habitsLD.value = ArrayList(habits)
    }

    fun decreaseProgress(habit: Habit) {
        if (habit.currentProgress > 0) {
            habit.currentProgress -= 1
        }

        habitsLD.value = ArrayList(habits)
    }
}