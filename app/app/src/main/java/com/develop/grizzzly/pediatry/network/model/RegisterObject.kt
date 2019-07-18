package com.develop.grizzzly.pediatry.network.model

data class RegisterObject constructor(val name : String,
                                      val lastname : String,
                                      val middlename : String,
                                      val email : String,
                                      val city : String,
                                      val phone : String,
                                      val mainId : Long,
                                      val password : String)