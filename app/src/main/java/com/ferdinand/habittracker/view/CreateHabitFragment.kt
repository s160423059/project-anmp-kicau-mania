package com.ferdinand.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ferdinand.habittracker.databinding.FragmentCreateHabitBinding
import com.ferdinand.habittracker.viewmodel.DashboardViewModel

class CreateHabitFragment : Fragment() {

    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var viewModel: DashboardViewModel

    private val iconOptions = arrayOf(
        "Water",
        "Exercise",
        "Book",
        "Meditation",
        "Other"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)

        setupSpinner()

        binding.txtFormError.visibility = View.GONE

        binding.btnCreateHabit.setOnClickListener {
            createHabit()
        }
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            iconOptions
        )

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerIcon.adapter = spinnerAdapter
    }

    private fun createHabit() {
        val name = binding.txtHabitName.text.toString().trim()
        val description = binding.txtDescription.text.toString().trim()
        val goalText = binding.txtGoal.text.toString().trim()
        val unit = binding.txtUnit.text.toString().trim()
        val iconName = binding.spinnerIcon.selectedItem.toString()

        val goal = goalText.toIntOrNull()

        if (name.isEmpty() || description.isEmpty() || goal == null || goal <= 0 || unit.isEmpty()) {
            binding.txtFormError.visibility = View.VISIBLE
            return
        }

        binding.txtFormError.visibility = View.GONE

        viewModel.addHabit(
            name = name,
            description = description,
            goal = goal,
            unit = unit,
            iconName = iconName
        )

        findNavController().popBackStack()
    }
}