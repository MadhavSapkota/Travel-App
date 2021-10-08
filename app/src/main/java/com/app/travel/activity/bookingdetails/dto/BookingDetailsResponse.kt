package com.app.travel.activity.bookingdetails.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BookingDetailsResponse {

        @SerializedName("status")
        @Expose
        var status: Boolean? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("data")
        @Expose
        var data: BookingDetailsData? = null
}