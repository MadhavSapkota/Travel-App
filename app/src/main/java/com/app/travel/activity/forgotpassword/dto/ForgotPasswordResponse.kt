package com.app.travel.activity.forgotpassword.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForgotPasswordResponse {

    @SerializedName("forgot_email")
    @Expose
    var forgot_email: String? = null

}