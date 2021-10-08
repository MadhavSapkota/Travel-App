package com.app.travel.activity.login.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginData {
    @SerializedName("client_id")
    @Expose
    var clientId: Int?=null

    @SerializedName("client_fullname")
    @Expose
    var clientFullname: String?=null

    @SerializedName("email")
    @Expose
    var email: String?=null

    @SerializedName("nationality")
    @Expose
    var nationality: String?=null

    @SerializedName("company_name")
    @Expose
    var companyName: String?=null

    @SerializedName("company_id")
    @Expose
    var companyId: Int?=null

    @SerializedName("branch_name")
    @Expose
    var branchName: String?=null

    @SerializedName("branch_code")
    @Expose
    var branchCode: Int?=null

    @SerializedName("user_id")
    @Expose
    var userId: Int?=null

    @SerializedName("user_name")
    @Expose
    var userName: String?=null

    @SerializedName("token")
    @Expose
    var token: String?=null
}