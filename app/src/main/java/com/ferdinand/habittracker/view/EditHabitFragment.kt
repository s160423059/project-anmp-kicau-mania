package com.ferdinand.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.ferdinand.habittracker.databinding.FragmentEditHabitBinding
import com.ferdinand.habittracker.viewmodel.DashboardViewModel

class EditHabitFragment : Fragment(), EditHabitListener {

    private lateinit var binding: FragmentEditHabitBinding
    private lateinit var viewModel: DashboardViewModel

    private val args: EditHabitFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)

        binding.listener = this

        viewModel.fetchHabit(args.habitId)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.habitLD.observe(viewLifecycleOwner) { habit ->
            binding.habit = habit
        }
    }

    override fun onSaveClick(v: View) {
        val currentHabit = binding.habit ?: return

        val goalText = binding.txtGoal.text.toString().trim()
        val goal = goalText.toIntOrNull()

        if (currentHabit.name.isBlank() ||
            currentHabit.description.isBlank() ||
            currentHabit.unit.isBlank() ||
            goal == null || goal <= 0
        ) {
            binding.txtFormError.visibility = View.VISIBLE
            return
        }

        binding.txtFormError.visibility = View.GONE

        currentHabit.goal = goal

        viewModel.updateHabit(currentHabit)

        android.widget.Toast.makeText(v.context, "Habit updated", android.widget.Toast.LENGTH_SHORT).show()
        v.findNavController().popBackStack()
    }
}