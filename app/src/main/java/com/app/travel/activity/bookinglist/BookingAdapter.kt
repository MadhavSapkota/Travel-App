package com.app.travel.activity.bookinglist
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.travel.activity.bookinglist.dto.BookingData
import com.app.travel.apputlis.AppUtils.format1
import com.app.travel.apputlis.AppUtils.formatter
import com.app.travel.databinding.ActivityBookingDataBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import java.text.ParseException
import java.util.*


class BookingAdapter(val context: Context) : RecyclerView.Adapter<BookingHolder>(), Filterable {
    private var transactionList = ArrayList<BookingData>()
    var clickSubject = PublishSubject.create<BookingData>()
    var startdate: Date? = null
    var enddate: Date? = null
    private var filteredList = transactionList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = ActivityBookingDataBinding.inflate(inflator, parent, false)
        val viewHolder = BookingHolder(view)
        bookingDataActionListner(viewHolder, parent)
        return viewHolder
    }

    //action listner for booking data
    private fun bookingDataActionListner(
        viewHolder: BookingHolder,
        parent: ViewGroup
    ) {
        RxView.clicks(viewHolder.btnPlace as View)
            .takeUntil(RxView.detaches(parent))
            .map { transactionList[viewHolder.adapterPosition] }
            .doOnEach {
                notifyDataSetChanged()
            }
            .subscribe(clickSubject)
    }

    override fun onBindViewHolder(holder: BookingHolder, position: Int) {
        val quickModel = transactionList[position]
        holder.tvName.text = quickModel.bookingTitle
        val (formatedStartDate, formatedEndDate) = setDateFormat(quickModel)
        holder.tvTime.text = quickModel.numOfDay + " days (" + formatedStartDate + " - " + formatedEndDate + ")"
        setDateFormat(quickModel)
        System.out.println("QuickModel is " + transactionList)
    }

    //set api date format according to requirements.
    private fun setDateFormat(quickModel: BookingData): Pair<String, String> {
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

    //to display the bookingdata
    fun showListItems(dashboardlist: ArrayList<BookingData>?, aboolean: Boolean) {
        when {aboolean -> transactionList.clear() }
        if (dashboardlist != null && !dashboardlist.isEmpty())
            this.transactionList.addAll(dashboardlist)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return transactionList.size
    }

    //Searchable functionality
    override fun getFilter(): Filter {
        val filter: Filter = object : Filter() {
            var filterResults = FilterResults()
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                if (charSequence == null || charSequence?.length == 0) {
                    System.out.println("CharSequences is " + charSequence)
                    filterResults.count = filteredList?.size!!
                    filterResults.values = filteredList
                } else {
                    val searchChr = charSequence.toString()
                    System.out.println("SearchChr " + searchChr)
                    val resultData: MutableList<BookingData> = java.util.ArrayList<BookingData>()
                    System.out.println("ResultData is" + resultData)
                    System.out.println("FilteredList is" + filteredList)

                    for (bookingData in filteredList!!) {
                        System.out.println("BookingData is$filteredList")
                        //search is done on the basis of trip details only
                        //user has to correctly insert the booking title as in api  i.e small letter or large letters.
                        if (bookingData.bookingTitle!!.contains(searchChr) || bookingData.bookingTitle!!.lowercase().contains(searchChr)) {
                            System.out.println("BookingTitle is" + bookingData.bookingTitle)
                            resultData.add(bookingData)
                        }
                        else{

                            val toast = Toast.makeText(context, "No results found", Toast.LENGTH_SHORT)
                            toast.show()
                            val handler = Handler()
                            handler.postDelayed(Runnable { toast.cancel() }, 1000)
                        }
                    }
                    filterResults.count = resultData.size
                    filterResults.values = resultData
                }
                return filterResults
            }
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                transactionList = results.values as ArrayList<BookingData>
                println("The value of list is$transactionList")
                notifyDataSetChanged()
            }
        }
        return filter
    }
}
