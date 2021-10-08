package com.app.travel.activity.bookingdetails
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.databinding.ActivityBookingDetailsDataBinding

class BookingDetailsHolder(val binding:ActivityBookingDetailsDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var tvTripTitle: TextView = binding.tvBackgroundTitle
    var ivBackgroundImage: ImageView = binding.ivRectangleBackground
    var ivItineraryButton:ImageView  = binding.ivItineraryDetails
    var tvPersonName: TextView = binding.tvPersonName
    var tvBookedBy: TextView = binding.tvBookedBy
    var tvGuestNumber: TextView = binding.tvTextGuestNumber
    var tvTourBooking:TextView = binding.tvDateTourBooking
    var tvDurationTravel:TextView = binding.tvDurationTravel
    var btnBack: Button = binding.buttonArrow


}


