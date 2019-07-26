package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.adapters.speciality.SpecialitiesAdapter
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel
import com.develop.grizzzly.pediatry.viewmodel.speciality.SpecialitiesViewModel
import kotlinx.android.synthetic.main.fragment_specialities.*
import kotlinx.coroutines.Dispatchers

class SpecialitiesFragment : Fragment() {


    private lateinit var adapter: SpecialitiesAdapter
    private lateinit var viewModel: SpecialitiesViewModel

    private val args: SpecialitiesFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_specialities, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel = SpecialitiesViewModel(args.specialityType)

        val model = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        }!!

        adapter = SpecialitiesAdapter(activity!!, model, args.specialityType)
        listSpecialities.adapter = adapter
        listSpecialities.layoutManager = LinearLayoutManager(activity)

        viewModel.specialitiesLiveData.observe(this, Observer {
            adapter.submitList(it)
        })

        super.onViewCreated(view, savedInstanceState)
    }
}