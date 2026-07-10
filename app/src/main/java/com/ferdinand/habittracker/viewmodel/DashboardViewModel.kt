package com.ferdinand.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ferdinand.habittracker.model.Habit
import com.ferdinand.habittracker.util.buildDb
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) :
    AndroidViewModel(application), CoroutineScope {

    val habitsLD = MutableLiveData<List<Habit>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()

    // dipakai EditHabitFragment untuk load 1 habit by id (lihat Week 9 "The ViewModel")
    val habitLD = MutableLiveData<Habit>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.postValue(true)
        errorLD.postValue(false)

        launch {
            val db = buildDb(getApplication())
            val habits = db.habitDao().selectAllHabit()
            habitsLD.postValue(habits)
            loadingLD.postValue(false)
        }
    }

    fun addHabit(
        name: String,
        description: String,
        goal: Int,
        unit: String,
        iconName: String
    ) {
        launch {
            val db = buildDb(getApplication())

            val newHabit = Habit(
                name = name,
                description = description,
                goal = goal,
                unit = unit,
                currentProgress = 0,
                iconName = iconName
            )

            db.habitDao().insertAll(newHabit)
            habitsLD.postValue(db.habitDao().selectAllHabit())
        }
    }

    fun increaseProgress(habit: Habit) {
        launch {
            val db = buildDb(getApplication())

            if (habit.currentProgress < habit.goal) {
                habit.currentProgress += 1
                db.habitDao().updateHabit(habit)
            }

            habitsLD.postValue(db.habitDao().selectAllHabit())
        }
    }

    fun decreaseProgress(habit: Habit) {
        launch {
            val db = buildDb(getApplication())

            if (habit.currentProgress > 0) {
                habit.currentProgress -= 1
                db.habitDao().updateHabit(habit)
            }

            habitsLD.postValue(db.habitDao().selectAllHabit())
        }
    }

    // dipanggil oleh EditHabitFragment untuk ambil 1 data habit berdasarkan id
    // (Week 9 "The ViewModel" -> fetch function)
    fun fetchHabit(id: Int) {
        launch {
            val db = buildDb(getApplication())
            habitLD.postValue(db.habitDao().selectHabit(id))
        }
    }

    fun updateHabit(habit: Habit) {
        launch {
            val db = buildDb(getApplication())
            db.habitDao().updateHabit(habit)
            habitsLD.postValue(db.habitDao().selectAllHabit())
        }
    }
}