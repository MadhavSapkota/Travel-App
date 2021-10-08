package com.app.travel.activity.itinerarylist.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItineraryListData {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("num_day")
    @Expose
    var numDay: String? = null

    @SerializedName("day_by_day")
    @Expose
    var dayByDay: String? = null

    @SerializedName("title_msg")
    @Expose
    var titleMsg: String? = null

}


