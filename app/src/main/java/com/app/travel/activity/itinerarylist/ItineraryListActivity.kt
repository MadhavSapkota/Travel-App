package com.app.travel.activity.itinerarylist
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.itinerarylist.di.DaggerItineraryListComponent
import com.app.travel.activity.itinerarylist.di.ItineraryListModule
import com.app.travel.activity.itinerarylist.mvp.ItineraryListPresenter
import com.app.travel.activity.itinerarylist.mvp.ItineraryListView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.FragmentItineraryListBinding
import javax.inject.Inject


class   ItineraryListActivity : AppCompatActivity() {

    @Inject
    lateinit var itineraryListView: ItineraryListView

    @Inject
    lateinit var itineraryListPresenter: ItineraryListPresenter

    private lateinit var binding: FragmentItineraryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication = AppApplication()
        DaggerItineraryListComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .itineraryListModule(ItineraryListModule(this))
            .build()
            .inject(this)
        binding = FragmentItineraryListBinding.inflate(getLayoutInflater())
        val view = binding.root
        itineraryListView.start(binding)
        setContentView(view)
        itineraryListPresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ItineraryListActivity::class.java))
        }
    }
}
