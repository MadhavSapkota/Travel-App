package com.app.travel.activity.bookingdetails.di
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component


@AppActivity
@Component(modules=[(BookingDetailsModule::class)],dependencies =[(AppComponent::class)])
interface BookingDetailsComponent{
    abstract fun inject(bookingDetailsActivity: BookingDetailsActivity)

}