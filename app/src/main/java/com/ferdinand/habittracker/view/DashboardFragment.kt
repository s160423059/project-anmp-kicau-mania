package com.ferdinand.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferdinand.habittracker.databinding.FragmentDashboardBinding
import com.ferdinand.habittracker.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel
    private val habitAdapter = HabitAdapter(
        arrayListOf(),
        onPlusClick = { habit ->
            viewModel.increaseProgress(habit)
        },
        onMinusClick = { habit ->
            viewModel.decreaseProgress(habit)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        binding.recViewHabits.layoutManager = LinearLayoutManager(context)
        binding.recViewHabits.adapter = habitAdapter

        observeViewModel()

        viewModel.refresh()
    }

    private fun observeViewModel() {
        viewModel.habitsLD.observe(viewLifecycleOwner) { habits ->
            habitAdapter.updateHabitList(habits)

            if (habits.isEmpty()) {
                binding.txtEmptyMessage.visibility = View.VISIBLE
                binding.recViewHabits.visibility = View.GONE
            } else {
                binding.txtEmptyMessage.visibility = View.GONE
                binding.recViewHabits.visibility = View.VISIBLE
            }
        }

        viewModel.loadingLD.observe(viewLifecycleOwner) {
            // Belum dipakai di tahap ini.
        }

        viewModel.errorLD.observe(viewLifecycleOwner) {
            // Belum dipakai di tahap ini.
        }
    }
}