package com.app.travel.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.activity.dashboard.dto.DashboardData
import com.app.travel.apputlis.AppUtils.format1
import com.app.travel.apputlis.AppUtils.formatter
import com.app.travel.databinding.ActivityDashboardDataBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter(val context: Context) : RecyclerView.Adapter<HomeHolder>(){
    private var transactionList = ArrayList<DashboardData>()
    var clickSubject = PublishSubject.create<DashboardData>()
    var startdate: Date? = null
    var enddate: Date? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = ActivityDashboardDataBinding.inflate(inflator, parent, false)
        val viewHolder = HomeHolder(view)


        /*This chunck of code deals for clickable(place) api items retrieved from api*/
        RxView.clicks(viewHolder.btnPlace as View)
            .takeUntil(RxView.detaches(parent))
            .map { transactionList[viewHolder.adapterPosition] }
            .doOnEach {
                notifyDataSetChanged()
            }
            .subscribe(clickSubject)
        return viewHolder
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val quickModel = transactionList[position]
        holder.tvName.text = quickModel.bookingTitle
        val (formatedStartDate, formatedEndDate) = setDateFormat(quickModel)
        holder.tvTime.text = quickModel.numOfDay + " days (" + formatedStartDate + " - " + formatedEndDate + ")"
        System.out.println("QuickModel is " + transactionList)
    }


    /*This functionality{@setDateFormat(params) is to convert and set the api date format according to our requirements.}*/
    private fun setDateFormat(quickModel: DashboardData): Pair<String, String> {
        formatter.isLenient = false
        try {
            startdate = formatter.parse(quickModel.startDate)
            enddate = formatter.parse(quickModel.endDate)
            println(startdate)
            println(enddate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatedStartDate = format1.format(startdate)
        val formatedEndDate = format1.format(enddate)
        return Pair(formatedStartDate, formatedEndDate)
    }


  /*This functionality{showListItems(params)} deals to display all the DashboardData*/
    fun showListItems(dashboardlist: ArrayList<DashboardData>?, aboolean: Boolean) {
        when {
            aboolean -> transactionList.clear()
        }
        if (dashboardlist != null && !dashboardlist.isEmpty())
            this.transactionList.addAll(dashboardlist)

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return transactionList.size
    }

}
