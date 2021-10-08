package com.app.travel.activity.changepassword.dto

data class ChangePasswordRequest(
  var old_password: String,
  var new_password: String,
  var confirm_password: String,
)
