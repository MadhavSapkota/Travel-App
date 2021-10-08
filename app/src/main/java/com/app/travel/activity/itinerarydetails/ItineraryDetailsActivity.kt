package com.app.travel.activity.itinerarydetails
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.itinerarydetails.di.DaggerItineraryDetailsComponent
import com.app.travel.activity.itinerarydetails.di.ItineraryDetailsModule
import com.app.travel.activity.itinerarydetails.mvp.ItineraryDetailsPresenter
import com.app.travel.activity.itinerarydetails.mvp.ItineraryDetailsView
import com.app.travel.activity.itinerarylist.di.DaggerItineraryListComponent
import com.app.travel.activity.itinerarylist.di.ItineraryListModule
import com.app.travel.activity.itinerarylist.mvp.ItineraryListPresenter
import com.app.travel.activity.itinerarylist.mvp.ItineraryListView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.FragmentItineraryDetailsBinding
import com.app.travel.databinding.FragmentItineraryListBinding
import javax.inject.Inject


class   ItineraryDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var itineraryDetailsView: ItineraryDetailsView

    @Inject
    lateinit var itineraryDetailsPresenter: ItineraryDetailsPresenter

    private lateinit var binding: FragmentItineraryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication = AppApplication()
        DaggerItineraryDetailsComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .itineraryDetailsModule(ItineraryDetailsModule(this))
            .build()
            .inject(this)

        binding = FragmentItineraryDetailsBinding.inflate(getLayoutInflater())
        val view = binding.root
        itineraryDetailsView.start(binding)
        setContentView(view)
        itineraryDetailsPresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ItineraryDetailsActivity::class.java))
        }
    }
}
