package com.app.travel.activity.bookingdetails
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.databinding.ActivityItinerarylistDataBinding

class ItineraryListHolder(val binding:ActivityItinerarylistDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
        var tvDayNumber:TextView = binding.tvDay1
        var tvTravelDuration:TextView = binding.tvDurationTravel
        var ivRectangle:ImageView  = binding.ivDay1
}
