package com.app.travel.activity.signup.dto

data class SignUpRequest(
    var client_fullname: String,
    var email: String,
    var nationality: String,
    var phone: String,
    var dob: String,
    var password: String,
    var cpassword: String
)
