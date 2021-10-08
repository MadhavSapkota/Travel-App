package com.app.travel.activity.itinerarylist.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.itinerarylist.ItineraryListAdapter
import com.app.travel.activity.itinerarylist.mvp.ItineraryListModel
import com.app.travel.activity.itinerarylist.mvp.ItineraryListPresenter
import com.app.travel.activity.itinerarylist.mvp.ItineraryListView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides


@Module
class ItineraryListModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getItineraryListView( itineraryListAdapter: ItineraryListAdapter): ItineraryListView {
        return ItineraryListView(appCompatActivity,itineraryListAdapter)
    }

    @Provides
    fun getItineraryListModel(webservice: Webservice): ItineraryListModel {
        return ItineraryListModel(appCompatActivity, webservice)
    }

    @Provides
    fun getItineraryListPresenter(
        itinerarylistView: ItineraryListView,
        itinerarylistModel: ItineraryListModel,

        ): ItineraryListPresenter {
        return ItineraryListPresenter(itinerarylistView, itinerarylistModel)
    }

    @Provides
    fun getItineraryListAdapter(): ItineraryListAdapter {
        return ItineraryListAdapter(appCompatActivity)
    }
}