package com.ferdinand.habittracker.model

data class Habit(
    val id: Int,
    val name: String,
    val description: String,
    val goal: Int,
    val unit: String,
    var currentProgress: Int,
    val iconName: String
) {
    fun isCompleted(): Boolean {
        return currentProgress >= goal
    }

    fun getStatusText(): String {
        return if (isCompleted()) {
            "Completed"
        } else {
            "In Progress"
        }
    }

    fun getProgressText(): String {
        return "$currentProgress / $goal $unit"
    }
}