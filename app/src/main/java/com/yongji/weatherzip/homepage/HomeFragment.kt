package com.yongji.weatherzip.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yongji.weatherzip.R
import com.yongji.weatherzip.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater)

        setHasOptionsMenu(true)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        return binding.root
    }


}