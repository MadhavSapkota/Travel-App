package com.app.travel.fragment.profile

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.R
import com.app.travel.activity.userprofile.dto.UserProfileData
import com.app.travel.apputlis.UserInfo.UserProfileImage
import com.app.travel.databinding.ActivityProfileDataBinding
import com.jakewharton.rxbinding2.view.RxView
import com.squareup.picasso.Picasso
import io.reactivex.subjects.PublishSubject
import java.io.File


class ProfileAdapter(val context: Context) : RecyclerView.Adapter<ProfileHolder>() {
    private var transactionList = ArrayList<UserProfileData>()
    var clickBackSubject = PublishSubject.create<UserProfileData>()/*arrowBack button*/
    var clickSubject = PublishSubject.create<UserProfileData>() /*editpassword button*/
    var clickSubjectChangePassword = PublishSubject.create<UserProfileData>() /*changepasswordbutton*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = ActivityProfileDataBinding.inflate(inflator, parent, false)
        val viewHolder = ProfileHolder(view)
        ivEditPassword(viewHolder, parent)
        ivChangePassword(viewHolder, parent)
        return viewHolder
    }


    //EditPassword action listner implementation
    private fun ivEditPassword(
        viewHolder: ProfileHolder,
        parent: ViewGroup
    ) {
        RxView.clicks(viewHolder.ivEditPassword as View)
            .takeUntil(RxView.detaches(parent))
            .map { transactionList[viewHolder.adapterPosition] }
            .doOnEach {
                notifyDataSetChanged()
            }
            .subscribe(clickSubject)
    }


    //ChangePassword action listner implementation
    private fun ivChangePassword(
        viewHolder: ProfileHolder,
        parent: ViewGroup
    ) {
        RxView.clicks(viewHolder.ivChangePassword as View)
            .takeUntil(RxView.detaches(parent))
            .map { transactionList[viewHolder.adapterPosition] }
            .doOnEach {
                notifyDataSetChanged()
            }
            .subscribe(clickSubjectChangePassword)
    }


    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        val quickModel = transactionList[position]
        holder.tvUsername.text = quickModel.clientFullname
        holder.tvEmail.text = quickModel.email
        holder.tvAddress.text = quickModel.nationality
        holder.tvPhone.text = quickModel.phone
        System.out.println("Image Value is" + UserProfileImage)

//        Picasso.with(getContext()).load(uri).centerCrop().resize(width, height).into(image);
        val file = File(Uri.parse(UserProfileImage).toString()).toUri()
        System.out.println("File is" + file)
        val f = File(file.toString())
        Picasso.get()
            .load(file)
            .placeholder(R.drawable.eclipse)
            .fit()
            .centerCrop()
            .into(holder.tvUserImage)
    }

    //show items of userprofiledata
    fun showListItems(dashboarddetails: UserProfileData, aboolean: Boolean) {
        when {
            aboolean -> transactionList.clear()
        }
        if (dashboarddetails != null)
            this.transactionList.addAll(listOf(dashboarddetails))
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }
}