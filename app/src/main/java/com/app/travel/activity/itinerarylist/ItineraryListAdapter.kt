package com.app.travel.activity.itinerarylist
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.activity.bookingdetails.ItineraryListHolder
import com.app.travel.activity.itinerarylist.dto.ItineraryListData
import com.app.travel.databinding.ActivityItinerarylistDataBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import kotlin.collections.ArrayList


class ItineraryListAdapter(val context: Context) : RecyclerView.Adapter<ItineraryListHolder>() {
    private var transactionList = ArrayList<ItineraryListData>()
    var clickSubject = PublishSubject.create<ItineraryListData>()/*viewItineray button*/
    var clickBackSubject = PublishSubject.create<ItineraryListData>()/*arrowBack button*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryListHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = ActivityItinerarylistDataBinding.inflate(inflator, parent, false)
        val viewHolder = ItineraryListHolder(view)
        ivItineraryButton(viewHolder, parent)
        return viewHolder
    }

    // this is for ivItineraryButton action listener
    private fun ivItineraryButton(
        viewHolder: ItineraryListHolder,
        parent: ViewGroup
    ) {
        RxView.clicks(viewHolder.ivRectangle as View)
            .takeUntil(RxView.detaches(parent))
            .map { transactionList[viewHolder.adapterPosition] }
            .doOnEach {
                notifyDataSetChanged()
            }
            .subscribe(clickSubject)
    }


    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ItineraryListHolder, position: Int) {
        val quickModel = transactionList[position]
        holder.tvDayNumber.text = "Day" + "\n" + quickModel.numDay
        holder.tvTravelDuration.text = quickModel.titleMsg
        System.out.println("QuickModel is " + transactionList)
    }


    //display itinerary list items
    fun showListItems(itinerarylist: List<ItineraryListData>?, aboolean: Boolean) {
        when {aboolean -> transactionList.clear() }
        if (itinerarylist != null)
            this.transactionList.addAll(itinerarylist)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return transactionList.size
    }
}