package com.app.travel.activity.bookingdetails.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsAdapter
import com.app.travel.activity.bookingdetails.mvp.BookingDetailsModel
import com.app.travel.activity.bookingdetails.mvp.BookingDetailsPresenter
import com.app.travel.activity.bookingdetails.mvp.BookingDetailsView
import com.app.travel.activity.bookinglist.BookingAdapter
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides



@Module
class BookingDetailsModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getBookingDetailsView(bookingDetailsAdapter: BookingDetailsAdapter): BookingDetailsView {
        return BookingDetailsView(appCompatActivity, bookingDetailsAdapter)
    }

    @Provides
    fun getBookingDetailsModel(webservice: Webservice): BookingDetailsModel {
        return BookingDetailsModel(appCompatActivity, webservice)
    }


    @Provides
    fun getBookingDetailsPresenter(
        bookingDetailsView: BookingDetailsView,
        bookingDetailsModel: BookingDetailsModel,

    ): BookingDetailsPresenter {
        return BookingDetailsPresenter(bookingDetailsView, bookingDetailsModel)
    }


    @Provides
    fun getBookingAdapter(): BookingDetailsAdapter {
        return BookingDetailsAdapter(appCompatActivity)
    }


}