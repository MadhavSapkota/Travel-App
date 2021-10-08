package com.app.travel.activity.itinerarydetails
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.databinding.ActivityItinerarydetailsDataBinding

class ItineraryDetailsHolder(val binding:ActivityItinerarydetailsDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
        var tvDayNumber: TextView = binding.tvTitleItineraryList
        var ivRectangleBox: ImageView = binding.imageView6
        var tvDurationItinerary:TextView = binding.tvTitleItineraryDuration
        var tvCondition:TextView = binding.tvTitleItineraryList3
        var tvGuideName: TextView = binding.tvGuide
        var tvGuideType:TextView = binding.tvGuideType
}
