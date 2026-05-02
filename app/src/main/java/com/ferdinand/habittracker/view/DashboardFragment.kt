package com.ferdinand.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ferdinand.habittracker.databinding.FragmentDashboardBinding
import com.ferdinand.habittracker.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel

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

        observeViewModel()

        viewModel.refresh()
    }

    private fun observeViewModel() {
        viewModel.habitsLD.observe(viewLifecycleOwner) { habits ->
            binding.txtHabitCount.text = "Jumlah habit: ${habits.size}"

            if (habits.isEmpty()) {
                binding.txtEmptyMessage.visibility = View.VISIBLE
            } else {
                binding.txtEmptyMessage.visibility = View.GONE
            }
        }

        viewModel.loadingLD.observe(viewLifecycleOwner) { isLoading ->
        }

        viewModel.errorLD.observe(viewLifecycleOwner) { isError ->
        }
    }
}