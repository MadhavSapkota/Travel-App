/**
 * The alert dialog is responsible for creating alert dialog.The main objective of this dialog is
 * to facilitate user interaction as logout from the dashboard.
 *
 *
 * Layout:: alert_layout.xml
 * Called in: DashboardView under getAlertMessage() function
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
import com.app.travel.activity.login.LoginActivity
import com.app.travel.activity.login.mvp.LoginView
import com.app.travel.fragment.home.mvp.HomeView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable

 class AlertDialog(
    private var appCompatActivity: AppCompatActivity,
    private var errorMessage: String,
    private var title: String,
    private var type: Int,
) {

    private var dialog: Dialog = Dialog(appCompatActivity)
    private var tvError: TextView
    private var cvOKBtn: Button
    private var cvNOBtn: Button
    private var dashboardView = DashboardView(appCompatActivity)


    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.setContentView(R.layout.alert_layout)
        tvError=dialog.findViewById(R.id.tvError)
        cvOKBtn=dialog.findViewById(R.id.btnYes)
        cvNOBtn = dialog.findViewById(R.id.btnNo)
        tvError.text=errorMessage
        title ="AlertBox"
        type = 1
        showDialog()
    }

    @SuppressLint("CheckResult")
    fun showDialog() {

        val lp= WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width= WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes=lp
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        getOKObservable().subscribe {
            dashboardView.startLoginActivity()
        }

        getNOObservable().subscribe{
            dismissDialog()
        }
    }

    //if a user presses Yes button
    fun getOKObservable(): Observable<Any> {
        return RxView.clicks(cvOKBtn)
    }

   //if a user presses No button
    fun getNOObservable(): Observable<Any> {
        return RxView.clicks(cvNOBtn)
    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}