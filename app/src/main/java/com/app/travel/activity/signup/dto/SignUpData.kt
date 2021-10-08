package com.app.travel.activity.signup.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpData {
        @SerializedName("client_fullname")
        @Expose
        var clientFullname: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("nationality")
        @Expose
        var nationality: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("dob")
        @Expose
        var dob: String? = null

        @SerializedName("password")
        @Expose
        var password: String? = null
    }
