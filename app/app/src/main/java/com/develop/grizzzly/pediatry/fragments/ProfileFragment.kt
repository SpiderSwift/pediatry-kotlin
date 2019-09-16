package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.adapters.menu.MenuAdapter
import com.develop.grizzzly.pediatry.databinding.FragmentProfileBinding
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.viewmodel.profile.ProfileViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment: Fragment() {

    private lateinit var model: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
        val mainActivity = activity
        mainActivity?.bottom_nav?.visibility = View.VISIBLE
        mainActivity?.toolbarTitle?.text = "Профиль"

        model = activity?.run {
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        }!!

        model.newAvatar.observe(this, Observer {
            if(it != null){
                profile_photo.setImageURI(it)
            }
        })

        binding.model = model
        binding.lifecycleOwner = this

        GlobalScope.launch {
            val response = WebAccess.pediatryApi.getProfile()
            if (response.isSuccessful) {
                val name = response.body()?.response?.name
                val lastname = response.body()?.response?.lastname
                val avatarUrl = "${response.body()?.response?.avatar}"
                withContext(Dispatchers.Main) {
                    model.name.postValue(name)
                    model.lastname.postValue(lastname)
                    model.avatarUrl.postValue(avatarUrl)
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        model.phoneNumber.observe(this, Observer {
            valuePhone.text = model.getNumber()
        })
        super.onViewCreated(view, savedInstanceState)
    }


}