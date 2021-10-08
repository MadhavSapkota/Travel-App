package com.app.travel.activity.itinerarydetails.di
import com.app.travel.activity.itinerarydetails.ItineraryDetailsActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component


@AppActivity
@Component(modules=[(ItineraryDetailsModule::class)],dependencies =[(AppComponent::class)])
interface ItineraryDetailsComponent{
    abstract fun inject(itineraryDetailsActivity: ItineraryDetailsActivity)
}