package com.app.travel.activity.editprofile.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EditProfileData {

    @SerializedName("client_fullname")
    @Expose
   var clientFullname: String? = null

    @SerializedName("dob")
    @Expose
    var dob: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("nationality")
    @Expose
    var nationality: String? = null

    @SerializedName("passport_no")
    @Expose
    var passportNo: String? = null

    @SerializedName("passport_expiry")
    @Expose
    var passportExpiry: String? = null

    @SerializedName("client_img")
    @Expose
    var clientImg: String? = null

    @SerializedName("passport_copy")
    @Expose
    var passportCopy: String? = null
}

