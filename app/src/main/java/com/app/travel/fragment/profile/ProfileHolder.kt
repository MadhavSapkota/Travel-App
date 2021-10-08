package com.app.travel.fragment.profile
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.databinding.ActivityProfileDataBinding

class ProfileHolder(val binding: ActivityProfileDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var tvUsername: TextView = binding.etUsername
    var tvEmail: TextView = binding.etEmailAddress
    var tvPhone: TextView = binding.etPhoneDetails
    var tvAddress: TextView = binding.etAddress
    var ivcameraIcon: ImageView = binding.imageView
    var tvUserImage: ImageView = binding.shapeableImageView
    var ivEditPassword: ImageView = binding.etEditPassword
    var ivChangePassword: ImageView = binding.etChangePassword

}