package com.app.travel.activity.bookinglist.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookinglist.BookingAdapter
import com.app.travel.activity.bookinglist.mvp.BookingModel
import com.app.travel.activity.bookinglist.mvp.BookingPresenter
import com.app.travel.activity.bookinglist.mvp.BookingView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides



@Module
class BookingModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getBookingView(bookingAdapter: BookingAdapter): BookingView{
        return BookingView(appCompatActivity, bookingAdapter)
    }

    @Provides
    fun getBookingModel(webservice: Webservice): BookingModel {
        return BookingModel(appCompatActivity, webservice)
    }


    @Provides
    fun getBookingPresenter(
        bookingView: BookingView,
        bookingModel: BookingModel,

    ): BookingPresenter {
        return BookingPresenter(bookingView, bookingModel)
    }

    @Provides
    fun getBookingAdapter(): BookingAdapter {
        return BookingAdapter(appCompatActivity)
    }

}