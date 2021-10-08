package com.app.travel.activity.dashboard.mvp
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.travel.R
import com.app.travel.activity.login.LoginActivity
import com.app.travel.apputlis.UserInfo
import com.app.travel.databinding.ActivityDashBoardBinding
import com.app.travel.dialog.AlertDialog
import com.app.travel.fragment.home.HomeFragment
import com.app.travel.fragment.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_dash_board.view.*


class DashboardView(
    private val appCompatActivity: AppCompatActivity,
) {
    var binding: ActivityDashBoardBinding? = null
    fun start(binding_dashboard: ActivityDashBoardBinding) {
        binding = binding_dashboard
        loadFragment(HomeFragment())
        getBottonNavigation()
        getNavigationListner()
        System.out.println("Dashboard LoginSatus" + UserInfo.loginStatus)
    }


   /*This functionaliy {@getNavigationListner} is for calling profile fragment, home fragment and to implement logout functionality
   on clicking bottom navigations items.*/
    fun getNavigationListner(){
        binding!!.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home->{
                    loadFragment(HomeFragment())
                    binding!!.bottomNavigation.menu.getItem(0).setIcon(R.drawable.ic_home)
                    binding!!.bottomNavigation.menu.getItem(2).setIcon(R.drawable.ic_icon_user)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_notification-> {
                    binding!!.bottomNavigation.menu.getItem(0).setIcon(R.drawable.ic_icon_home_next)
                    binding!!.bottomNavigation.menu.getItem(2).setIcon(R.drawable.ic_icon_user)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_person-> {
                    loadFragment(ProfileFragment())
                    binding!!.bottomNavigation.menu.getItem(0).setIcon(R.drawable.ic_icon_home_next)
                    binding!!.bottomNavigation.menu.getItem(2).setIcon(R.drawable.icon_user)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_signout->{
                    getAlertMessage("Do you want to exit ?","",1)
                    binding!!.bottomNavigation.menu.getItem(0).setIcon(R.drawable.ic_icon_home_next)
                    binding!!.bottomNavigation.menu.getItem(2).setIcon(R.drawable.ic_icon_user)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    /*This {@getAlertMessage} functionality is for retreieving and setting api errors messages to the alert dialog box.*/
    fun getAlertMessage(message:String,title: String, type: Int){
        appCompatActivity.runOnUiThread(Runnable {
            AlertDialog(appCompatActivity, message!!,title!!,type)
        })
    }

    /*This{@getBottonNavigation} functionality is to set the color of the bottom navigations items to its original color*/
    fun getBottonNavigation(){
        val bottomNavigation = binding!!.bottomNavigation
        bottomNavigation.enableAnimation(false)
        bottomNavigation.enableShiftingMode(false)
        bottomNavigation.enableItemShiftingMode(false)
    }


    /*This {@loadFragment} functionality is to load the fragment with the transcations operations. */
    private fun loadFragment(fragment: Fragment) {
        val transaction=appCompatActivity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view_dashboard, fragment)
        transaction.commit()
    }

    /*
    This { @startfunctionality()} funtionality is for alertbox logout.This Triggers alert dialog to call LoginActivity when a users presses "OK" in
     the alert dialog in the dashboard activity.And also set the LoginStatus of SharedPreferences is false.
     */
    fun startLoginActivity(){
        UserInfo.loginStatus = false
        this.appCompatActivity.finishAffinity()
        LoginActivity.start(appCompatActivity)
    }

    fun closeDashboardActivity(){
        this.appCompatActivity.finishAffinity()
    }

}