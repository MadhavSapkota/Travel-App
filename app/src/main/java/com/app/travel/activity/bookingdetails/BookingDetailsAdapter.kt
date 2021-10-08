package com.app.travel.activity.bookingdetails
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.activity.bookingdetails.dto.BookingDetailsData
import com.app.travel.apputlis.AppUtils
import com.app.travel.apputlis.AppUtils.format1
import com.app.travel.apputlis.AppUtils.formatter
import com.app.travel.databinding.ActivityBookingDetailsDataBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


 class BookingDetailsAdapter(val context: Context) : RecyclerView.Adapter<BookingDetailsHolder>(){

    private var transactionList = ArrayList<BookingDetailsData>()

    //viewItineray button
    var clickSubject = PublishSubject.create<BookingDetailsData>()

    //arrowBack button
    var clickBackSubject = PublishSubject.create<BookingDetailsData>()
    var startdate: Date? = null
    var enddate: Date? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingDetailsHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = ActivityBookingDetailsDataBinding.inflate(inflator, parent, false)
        val viewHolder = BookingDetailsHolder(view)
        ivItineraryButton(viewHolder, parent)
        backArrowFunction(viewHolder, parent)
        return viewHolder
    }

     // this is for ivItineraryButton action listener
     private fun ivItineraryButton(
         viewHolder: BookingDetailsHolder,
         parent: ViewGroup
     ) {
         RxView.clicks(viewHolder.ivItineraryButton as View)
             .takeUntil(RxView.detaches(parent))
             .map { transactionList[viewHolder.adapterPosition] }
             .doOnEach {
                 notifyDataSetChanged()
             }
             .subscribe(clickSubject)
     }

     // this is for back arrow button action listener
     private fun backArrowFunction(
         viewHolder: BookingDetailsHolder, parent: ViewGroup) {
         RxView.clicks(viewHolder.btnBack as View)
             .takeUntil(RxView.detaches(parent))
             .map { transactionList[viewHolder.adapterPosition] }
             .doOnEach {
                 notifyDataSetChanged()
             }
             .subscribe(clickBackSubject)
     }

     override fun onBindViewHolder(holder: BookingDetailsHolder, position: Int) {
        val quickModel = transactionList[position]
        holder.tvTripTitle.text = quickModel.bookingTitle

         //teamlead capitalization and setup
         var capitalFirstLetter = quickModel.teamLead!!.substring(0,1).uppercase()
         var remainingLetter = quickModel.teamLead!!.substring(1)
         var fullName = capitalFirstLetter + remainingLetter
         holder.tvPersonName.text = fullName

        holder.tvDurationTravel.text = quickModel.numOfDay + " day"
        val (formatedStartDate, formatedEndDate) = setDateFormat(quickModel)
        holder.tvTourBooking.text =  formatedStartDate + " - " + formatedEndDate
        System.out.println("QuickModel is " + transactionList)
    }

    /*set api date format according to requirements.*/
    private fun setDateFormat(quickModel: BookingDetailsData): Pair<String, String> {
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

     //View Booking Details items
    fun showListItems(bookingDetailsData: BookingDetailsData?, aboolean: Boolean) {
        when {aboolean -> transactionList.clear() }
        if (bookingDetailsData != null)
            this.transactionList.addAll(listOf(bookingDetailsData))
         notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return transactionList.size
    }
}
