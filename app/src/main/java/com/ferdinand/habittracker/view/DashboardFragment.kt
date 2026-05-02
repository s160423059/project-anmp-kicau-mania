package com.ferdinand.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferdinand.habittracker.R
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

        viewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)

        binding.recViewHabits.layoutManager = LinearLayoutManager(context)
        binding.recViewHabits.adapter = habitAdapter

        binding.fabAddHabit.setOnClickListener {
            it.findNavController().navigate(R.id.action_dashboard_to_create_habit)
        }

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
        }

        viewModel.errorLD.observe(viewLifecycleOwner) {
        }
    }
}