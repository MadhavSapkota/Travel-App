package com.app.travel.activity.itinerarylist.di
import com.app.travel.activity.itinerarylist.ItineraryListActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component


@AppActivity
@Component(modules=[(ItineraryListModule::class)],dependencies =[(AppComponent::class)])
interface ItineraryListComponent{
    abstract fun inject(itineraryListActivity: ItineraryListActivity)

}