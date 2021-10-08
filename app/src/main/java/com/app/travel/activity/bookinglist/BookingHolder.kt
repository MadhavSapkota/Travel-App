package com.app.travel.activity.bookinglist
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.databinding.ActivityBookingDataBinding

class BookingHolder(val binding: ActivityBookingDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var tvName: TextView = binding.textViewGrandrukName
    var tvTime: TextView = binding.tvGrandrukTripDetails
    var btnPlace: ImageView = binding.btnGhandruk
    var ivRectangle: ImageView = binding.imageView5
}



