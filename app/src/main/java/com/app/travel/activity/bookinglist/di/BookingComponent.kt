package com.app.travel.activity.bookinglist.di
import com.app.travel.activity.bookinglist.BookingActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component

@AppActivity
@Component(modules=[(BookingModule::class)],dependencies =[(AppComponent::class)])
interface BookingComponent {
    abstract fun inject(bookingFragment: BookingActivity)

}