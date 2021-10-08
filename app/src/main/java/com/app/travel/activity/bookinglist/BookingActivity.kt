package com.app.travel.activity.bookinglist
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookinglist.di.BookingModule
import com.app.travel.activity.bookinglist.di.DaggerBookingComponent
import com.app.travel.activity.bookinglist.mvp.BookingPresenter
import com.app.travel.activity.bookinglist.mvp.BookingView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.FragmentBookingBinding
import javax.inject.Inject


class BookingActivity : AppCompatActivity() {
    @Inject
    lateinit var bookingView: BookingView
    @Inject
    lateinit var bookingPresenter: BookingPresenter
    private lateinit var binding: FragmentBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication = AppApplication()
        DaggerBookingComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .bookingModule(BookingModule(this))
            .build()
            .inject(this)
        binding = FragmentBookingBinding.inflate(getLayoutInflater())
        val view = binding.root
        bookingView.start(binding)
        setContentView(view)
        bookingPresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, BookingActivity::class.java))
        }
    }
}