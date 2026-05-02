package com.ferdinand.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ferdinand.habittracker.databinding.FragmentCreateHabitBinding

class CreateHabitFragment : Fragment() {

    private lateinit var binding: FragmentCreateHabitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }
}