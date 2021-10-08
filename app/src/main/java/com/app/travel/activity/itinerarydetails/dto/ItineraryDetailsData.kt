package com.app.travel.activity.itinerarydetails.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItineraryDetailsData {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("first_img")
    @Expose
    var firstImg: String? = null

    @SerializedName("second_img")
    @Expose
    var secondImg: String? = null

    @SerializedName("title_msg")
    @Expose
    var titleMsg: String? = null

    @SerializedName("left_msg")
    @Expose
    var leftMsg: String? = null

    @SerializedName("right_msg")
    @Expose
    var rightMsg: String? = null

    @SerializedName("team_lead")
    @Expose
    var teamLead: String? = null

    @SerializedName("num_day")
    @Expose
    var numDay: String? = null

    @SerializedName("day_by_day")
    @Expose
    var dayByDay: String? = null

    @SerializedName("hotel")
    @Expose
    var hotel: String? = null

    @SerializedName("room")
    @Expose
    var room: String? = null

    @SerializedName("meal")
    @Expose
    var meal: String? = null

    @SerializedName("vehicle")
    @Expose
    var vehicle: String? = null

    @SerializedName("guide_type")
    @Expose
    var guideType: String? = null

    @SerializedName("all_language")
    @Expose
    var allLanguage: String? = null

    @SerializedName("guide")
    @Expose
    var guide: String? = null

    @SerializedName("permit")
    @Expose
    var permit: String? = null

    @SerializedName("entrance")
    @Expose
    var entrance: String? = null

    @SerializedName("flight")
    @Expose
    var flight: String? = null

    @SerializedName("extras")
    @Expose
    var extras: String? = null
}


