package com.app.travel.activity.bookingdetails.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BookingDetailsData {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("booking_title")
    @Expose
    var bookingTitle: String? = null

    @SerializedName("start_date")
    @Expose
    var startDate: String? = null

    @SerializedName("team_lead")
    @Expose
    var teamLead: String? = null

    @SerializedName("end_date")
    @Expose
    var endDate: String? = null

    @SerializedName("num_of_day")
    @Expose
    var numOfDay: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

}


