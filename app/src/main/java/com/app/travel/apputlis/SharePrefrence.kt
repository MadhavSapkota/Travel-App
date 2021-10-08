package com.app.travel.apputlis
import com.chibatching.kotpref.KotprefModel

/*this is sharedpreferences file where values related to the sharedpreferences
of this project is stored here.*/

object UserInfo : KotprefModel() {
    var fullname by stringPref()
    var clientdId by stringPref()
    var email by stringPref()
    var nationality by stringPref()
    var companyname by stringPref()
    var companyid by stringPref()
    var branchname by stringPref()
    var branchcode by stringPref()
    var userid by stringPref()
    var username by stringPref()
    var token by stringPref()
    var clientFullname by stringPref()
    var dob by stringPref()
    var password by stringPref()
    var confirmpassword by stringPref()
    var phone by stringPref()
    var forgot_email by stringPref()
    var id by intPref()
    var itineraryId by intPref()
    var tourname by stringPref()
    var loginStatus by booleanPref(default = false)
    var UserProfileImage by stringPref()
}
