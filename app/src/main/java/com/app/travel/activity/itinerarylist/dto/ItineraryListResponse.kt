package com.app.travel.activity.itinerarylist.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItineraryListResponse {

        @SerializedName("status")
        @Expose
        var status: Boolean? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("data")
        @Expose
        var data: List<ItineraryListData>? = null

}