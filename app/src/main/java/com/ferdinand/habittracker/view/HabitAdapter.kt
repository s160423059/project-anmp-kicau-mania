package com.ferdinand.habittracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferdinand.habittracker.databinding.ItemHabitBinding
import com.ferdinand.habittracker.model.Habit

class HabitAdapter(
    private val habitList: ArrayList<Habit>,
    private val onPlusClick: (Habit) -> Unit,
    private val onMinusClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(val binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HabitViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        holder.binding.txtHabitName.text = habit.name
        holder.binding.txtDescription.text = habit.description
        holder.binding.txtStatus.text = habit.getStatusText()
        holder.binding.txtProgressText.text = habit.getProgressText()
        holder.binding.txtIcon.text = getIconText(habit.iconName)

        holder.binding.progressHabit.max = habit.goal
        holder.binding.progressHabit.progress = habit.currentProgress

        val canIncrease = habit.currentProgress < habit.goal
        val canDecrease = habit.currentProgress > 0

        holder.binding.btnPlus.isEnabled = canIncrease
        holder.binding.btnMinus.isEnabled = canDecrease

        holder.binding.btnPlus.alpha = if (canIncrease) 1.0f else 0.4f
        holder.binding.btnMinus.alpha = if (canDecrease) 1.0f else 0.4f

        holder.binding.btnPlus.setOnClickListener {
            onPlusClick(habit)
        }

        holder.binding.btnMinus.setOnClickListener {
            onMinusClick(habit)
        }
    }

    private fun getIconText(iconName: String): String {
        return when (iconName) {
            "Water" -> "💧"
            "Exercise" -> "💪"
            "Book" -> "📚"
            "Meditation" -> "🧘"
            else -> "⭐"
        }
    }

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }
}