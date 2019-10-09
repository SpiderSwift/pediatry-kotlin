package com.develop.grizzzly.pediatry.viewmodel.speciality

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import com.develop.grizzzly.pediatry.network.model.Speciality
import com.develop.grizzzly.pediatry.viewmodel.registration.RegistrationViewModel

class SpecialityItemViewModel constructor(
    private val model: RegistrationViewModel,
    private val activity: Activity,
    val speciality: Speciality,
    val type: Int
) : ViewModel() {

    fun onSpeciality(view: View) {
        when {
            (type == 0) -> {
                model.mainSpeciality.value = speciality
            }
            (type == 1) -> {
                model.firstAdditionalSpeciality.value = speciality
            }
            (type == 2) -> {
                model.secondAdditionalSpeciality.value = speciality
            }
        }
        activity.onBackPressed()
    }

}