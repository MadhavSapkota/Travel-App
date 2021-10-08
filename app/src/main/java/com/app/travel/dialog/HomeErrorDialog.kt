/*
The main purpose of creating this dialog box is , if boooking items are empty(not registered), users get message information that booking list is empty
and get out from the system on clicking the Ok button.
 */
package com.app.travel.dialog
import android.annotation.SuppressLint
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.R
import com.app.travel.activity.dashboard.mvp.DashboardView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable


class HomeErrorDailog(
    private var appCompatActivity: AppCompatActivity,
    private var errorMessage: String,
) {

    private var dialog: Dialog=Dialog(appCompatActivity)
    private var tvError: TextView
    private var cvOKBtn: Button
    private var dashboardView = DashboardView(appCompatActivity)


    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.setContentView(R.layout.error_layout)
        tvError=dialog.findViewById(R.id.tvError)
        cvOKBtn=dialog.findViewById(R.id.btnOK)
        tvError.text=errorMessage
        showDialog()
    }

    @SuppressLint("CheckResult")
    fun showDialog() {

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = lp
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        getOKObservable().subscribe {
            dashboardView.startLoginActivity()
        }
    }

    fun getOKObservable(): Observable<Any> {
        return RxView.clicks(cvOKBtn)
    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}




