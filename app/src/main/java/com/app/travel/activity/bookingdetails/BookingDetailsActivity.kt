package com.app.travel.activity.bookingdetails
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.di.BookingDetailsModule
import com.app.travel.activity.bookingdetails.di.DaggerBookingDetailsComponent
import com.app.travel.activity.bookingdetails.mvp.BookingDetailsPresenter
import com.app.travel.activity.bookingdetails.mvp.BookingDetailsView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.FragmentBookingDetailsBinding
import javax.inject.Inject


class BookingDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var bookingDetailsView: BookingDetailsView

    @Inject
    lateinit var bookingDetailsPresenter: BookingDetailsPresenter

    private lateinit var binding: FragmentBookingDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication = AppApplication()
        DaggerBookingDetailsComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .bookingDetailsModule(BookingDetailsModule(this))
            .build()
            .inject(this)


        binding = FragmentBookingDetailsBinding.inflate(getLayoutInflater())
        val view = binding.root
        bookingDetailsView.start(binding)
        setContentView(view)
        bookingDetailsPresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, BookingDetailsActivity::class.java))
        }
    }
}
