package com.app.travel.fragment.home

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.databinding.ActivityDashboardDataBinding

class HomeHolder(val binding: ActivityDashboardDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var tvName: TextView = binding.textViewGrandrukName
    var tvTime: TextView = binding.tvGrandrukTripDetails
    var btnPlace: ImageView = binding.btnGhandruk
    var ivRectangle: ImageView = binding.imageView5
}