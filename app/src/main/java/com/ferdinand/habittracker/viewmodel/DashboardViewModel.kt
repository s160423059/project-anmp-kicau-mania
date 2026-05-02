package com.ferdinand.habittracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ferdinand.habittracker.model.Habit
import com.ferdinand.habittracker.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()

    private val habits = arrayListOf<Habit>()
    private var nextId = 1

    private val fileHelper = FileHelper(getApplication())
    private val gson = Gson()

    init {
        loadHabitsFromFile()
    }

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

        saveHabitsToFile()
        habitsLD.value = ArrayList(habits)
    }

    fun increaseProgress(habit: Habit) {
        val selectedHabit = habits.find { it.id == habit.id }

        if (selectedHabit != null && selectedHabit.currentProgress < selectedHabit.goal) {
            selectedHabit.currentProgress += 1
            saveHabitsToFile()
        }

        habitsLD.value = ArrayList(habits)
    }

    fun decreaseProgress(habit: Habit) {
        val selectedHabit = habits.find { it.id == habit.id }

        if (selectedHabit != null && selectedHabit.currentProgress > 0) {
            selectedHabit.currentProgress -= 1
            saveHabitsToFile()
        }

        habitsLD.value = ArrayList(habits)
    }

    private fun saveHabitsToFile() {
        val jsonString = gson.toJson(habits)
        fileHelper.writeToFile(jsonString)

        Log.d("habit_file_write", jsonString)
        Log.d("habit_file_path", fileHelper.getFilePath())
    }

    private fun loadHabitsFromFile() {
        val jsonString = fileHelper.readFromFile()

        if (jsonString.isEmpty()) {
            habits.clear()
            nextId = 1
            habitsLD.value = ArrayList(habits)
            return
        }

        try {
            val sType = object : TypeToken<ArrayList<Habit>>() {}.type
            val savedHabits = gson.fromJson<ArrayList<Habit>>(jsonString, sType)

            habits.clear()

            if (savedHabits != null) {
                habits.addAll(savedHabits)
            }

            nextId = if (habits.isEmpty()) {
                1
            } else {
                habits.maxOf { it.id } + 1
            }

            habitsLD.value = ArrayList(habits)

            Log.d("habit_file_read", habits.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            errorLD.value = true
        }
    }
}