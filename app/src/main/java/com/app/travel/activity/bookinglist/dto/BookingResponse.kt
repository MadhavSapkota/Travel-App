package com.app.travel.activity.bookinglist.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class  BookingResponse {

        @SerializedName("status")
        @Expose
        var status: Boolean? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("data")
        @Expose
        var data: ArrayList<BookingData>? = null


    }