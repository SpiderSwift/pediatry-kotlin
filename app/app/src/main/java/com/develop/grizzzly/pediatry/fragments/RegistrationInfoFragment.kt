package com.develop.grizzzly.pediatry.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentRegistrationInfoBinding
import com.develop.grizzzly.pediatry.extensions.addMask
import com.develop.grizzzly.pediatry.images.minimizeImage
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration_info.*

private const val TAG = "REGISTRATION INFO F"

class RegistrationInfoFragment : Fragment() {

    lateinit var model: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegistrationInfoBinding>(
            inflater, R.layout.fragment_registration_info, container, false
        )
        binding.tePhone.addMask("+7 ([000]) [000]-[00]-[00]")
        model = activity?.run {
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
        }!!
        model.fragment = this
        binding.model = model
        binding.lifecycleOwner = this
        model.city.observe(this, Observer {
            teCity.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                if (model.isValidCity()) R.drawable.ic_cross
                else R.drawable.ic_light_cross,
                0
            )
            model.infoValid.value = model.isInfoValid()
        })
        model.phoneNumber.observe(this, Observer {
            model.infoValid.value = model.isInfoValid()
            tePhone.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                if (model.isValidPhone()) R.drawable.ic_cross
                else R.drawable.ic_light_cross,
                0
            )
        })
        model.fullname.observe(this, Observer {
            model.infoValid.value = model.isInfoValid()
            teFullName.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                if (model.isValidFullname()) R.drawable.ic_cross
                else R.drawable.ic_light_cross,
                0
            )
        })
        return binding.root
    }

    override fun onDetach() {
        model.clearInfo()
        super.onDetach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data ?: return
        if (resultCode == Activity.RESULT_OK) {
            val finalImage = data.data?.let {
                minimizeImage(it, context!!.contentResolver)
            }
            model.imageUrl.postValue(finalImage)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}