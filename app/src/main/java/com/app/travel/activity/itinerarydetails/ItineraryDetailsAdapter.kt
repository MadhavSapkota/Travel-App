package com.app.travel.activity.itinerarydetails
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.activity.itinerarydetails.dto.ItineraryDetailsData
import com.app.travel.databinding.ActivityItinerarydetailsDataBinding
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class ItineraryDetailsAdapter(val context: Context) :
    RecyclerView.Adapter<ItineraryDetailsHolder>() {
    private var transactionList = ArrayList<ItineraryDetailsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryDetailsHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = ActivityItinerarydetailsDataBinding.inflate(inflator, parent, false)
        val viewHolder = ItineraryDetailsHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItineraryDetailsHolder, position: Int) {
        val quickModel = transactionList[position]
        holder.tvDayNumber.text = "Day" + "  " + quickModel.numDay
        holder.tvCondition.text = "*Timings can be change dependings on conditions"
        holder.tvGuideName.text = "Guide Name: " + quickModel.guide
        holder.tvGuideType.text = "Guide Type: " + quickModel.guideType
        holder.tvDurationItinerary.text = "10.30 am"
        val url: String? = quickModel.secondImg

        Picasso.get().load(url).into(holder.ivRectangleBox)
        System.out.println("QuickModel is " + transactionList)
    }

    //itinerary details items
    fun showListItems(dashboarddetails: ItineraryDetailsData, aboolean: Boolean) {
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