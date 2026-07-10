package com.ferdinand.habittracker.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class Habit(
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "goal")
    var goal: Int,

    @ColumnInfo(name = "unit")
    var unit: String,

    @ColumnInfo(name = "currentProgress")
    var currentProgress: Int,

    @ColumnInfo(name = "iconName")
    var iconName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

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