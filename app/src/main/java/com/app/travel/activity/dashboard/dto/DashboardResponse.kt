package com.app.travel.activity.dashboard.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DashboardResponse {

        @SerializedName("status")
        @Expose
        var status: Boolean? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("data")
        @Expose
        var data: ArrayList<DashboardData>? = null
    }