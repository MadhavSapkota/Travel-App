package com.app.travel.activity.editprofile.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EditProfileResponse {
        @SerializedName("status")
        @Expose
        var status: Boolean? = null

        @SerializedName("message")
        @Expose
       var message: String? = null

        @SerializedName("data")
        @Expose
        var data: EditProfileData? = null

}