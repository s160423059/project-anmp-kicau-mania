package com.ferdinand.habittracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferdinand.habittracker.databinding.ItemHabitBinding
import com.ferdinand.habittracker.model.Habit
import kotlinx.coroutines.launch

class HabitAdapter(
    private val habitList: ArrayList<Habit>,
    private val onPlusClick: (Habit) -> Unit,
    private val onMinusClick: (Habit) -> Unit,
    private val onTitleClick: (Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>(), HabitCardListener {

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
        holder.binding.habit = habitList[position]
        holder.binding.listener = this
    }


    override fun onPlusClick(habit: Habit) {
        onPlusClick.invoke(habit)
    }

    override fun onMinusClick(habit: Habit) {
        onMinusClick.invoke(habit)
    }

    override fun onTitleClick(v: android.view.View) {
        val habitId = v.tag.toString().toInt()
        onTitleClick.invoke(habitId)
    }

    fun updateHabitList(newHabitList: List<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }
}