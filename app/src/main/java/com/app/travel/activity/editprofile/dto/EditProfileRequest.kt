package com.app.travel.activity.editprofile.dto

data class EditProfileRequest(
  var client_fullname: String,
  var dob: String,
  var email: String,
  var phone: String,
  var nationality: String
)
