package com.ferdinand.habittracker.view

import android.view.View
import com.ferdinand.habittracker.model.Habit

interface HabitCardListener {
    fun onPlusClick(habit: Habit)
    fun onMinusClick(habit: Habit)
    fun onTitleClick(v: View)
}