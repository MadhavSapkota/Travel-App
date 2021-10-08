package com.app.travel.activity.changepassword.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChangePasswordData {

    @SerializedName("old_password")
    @Expose
    var old_password: String? = null

    @SerializedName("new_password")
    @Expose
    var new_password: String? = null

    @SerializedName("confirm_password")
    @Expose
    var confirm_password: String? = null
}

