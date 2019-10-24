package com.develop.grizzzly.pediatry.fragments

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.databinding.FragmentLoginBinding
import com.develop.grizzzly.pediatry.extensions.isEmail
import com.develop.grizzzly.pediatry.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        val model = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.model = model
        binding.lifecycleOwner = this
        model.email.observe(this, Observer {
            teEmail.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                if (model.email.value?.isEmail() == true) R.drawable.ic_cross
                else R.drawable.ic_light_cross,
                0
            )
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tePassword.transformationMethod = PasswordTransformationMethod.getInstance()
        tePassword.setOnTouchListener(OnTouchListener { _, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= tePassword.right - tePassword.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    //tePassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    if (tePassword.transformationMethod is HideReturnsTransformationMethod) {
                        tePassword.transformationMethod = PasswordTransformationMethod.getInstance()
                        tePassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_light_eye, 0)
                    } else {
                        tePassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        tePassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0)
                    }
                    if (tePassword.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                        tePassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0)
                        tePassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    }
                    return@OnTouchListener true
                }
            }
            false
        })
        super.onViewCreated(view, savedInstanceState)
    }

}


