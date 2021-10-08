package com.app.travel.activity.itinerarydetails.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.itinerarydetails.ItineraryDetailsAdapter
import com.app.travel.activity.itinerarydetails.mvp.ItineraryDetailsModel
import com.app.travel.activity.itinerarydetails.mvp.ItineraryDetailsPresenter
import com.app.travel.activity.itinerarydetails.mvp.ItineraryDetailsView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides


@Module
class ItineraryDetailsModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getItineraryDetailsView( itineraryDetailsAdapter: ItineraryDetailsAdapter): ItineraryDetailsView {
        return ItineraryDetailsView(appCompatActivity, itineraryDetailsAdapter)
    }

    @Provides
    fun getItineraryDetailsModel(webservice: Webservice): ItineraryDetailsModel {
        return ItineraryDetailsModel(appCompatActivity, webservice)
    }

    @Provides
    fun getItineraryDetailsPresenter(
        itineraryDetailsView: ItineraryDetailsView,
        itineraryDetailsModel: ItineraryDetailsModel,

        ): ItineraryDetailsPresenter {
        return ItineraryDetailsPresenter(itineraryDetailsView, itineraryDetailsModel)
    }

    @Provides
    fun getItineraryDetailsAdapter(): ItineraryDetailsAdapter {
        return ItineraryDetailsAdapter(appCompatActivity)
    }
}