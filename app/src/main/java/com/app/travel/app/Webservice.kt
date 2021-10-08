package com.app.travel.app
import com.app.travel.activity.bookingdetails.dto.BookingDetailsResponse
import com.app.travel.activity.bookinglist.dto.BookingResponse
import com.app.travel.activity.changepassword.dto.ChangePasswordRequest
import com.app.travel.activity.changepassword.dto.ChangePasswordResponse
import com.app.travel.activity.dashboard.dto.DashboardResponse
import com.app.travel.activity.editprofile.dto.EditProfileData
import com.app.travel.activity.editprofile.dto.EditProfileRequest
import com.app.travel.activity.editprofile.dto.EditProfileResponse
import com.app.travel.activity.forgotpassword.dto.*
import com.app.travel.activity.itinerarydetails.dto.ItineraryDetailsResponse
import com.app.travel.activity.itinerarylist.dto.ItineraryListResponse
import com.app.travel.activity.login.dto.*
import com.app.travel.activity.signup.dto.*
import com.app.travel.activity.userprofile.dto.UserProfileResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface Webservice {
    @POST(Apiurl.LOGIN)
    fun postLogin(@Body info: LoginRequest): Observable<LoginResponse>

    @POST(Apiurl.SIGNUP)
    fun postSignUp(@Body info: SignUpRequest): Observable<SignUpResponse>

    @POST(Apiurl.FORGOTPASSWORD)
    fun postForgotPassword(@Body info: ForgotPasswordRequest): Observable<ForgotPasswordResponse>

    @GET(Apiurl.DASHBOARD)
    fun postDashboard(@Header("Authorization") token: DashboardResponse): Observable<DashboardResponse>

    @GET(Apiurl.DASHBOARD)
    fun postDashboard(@Header("Authorization") token: BookingResponse): Observable<BookingResponse>

    @GET(Apiurl.BOOKINGDETAILS)
    fun postBookingDetails(@Header("Authorization") token: BookingDetailsResponse, @Query("id") id: Int): Observable<BookingDetailsResponse>

    @GET(Apiurl.ITINERARYLIST)
    fun getitineraryList(@Header("Authorization") token: ItineraryListResponse): Observable<ItineraryListResponse>

    @GET(Apiurl.ITINERARYDETAILS)
    fun getitineraryDetails(@Header("Authorization") token: ItineraryDetailsResponse, @Query("id") id: Int): Observable<ItineraryDetailsResponse>

    @GET(Apiurl.USERPROFILE)
    fun getprofileDetails(@Header("Authorization") token: UserProfileResponse): Observable<UserProfileResponse>

    @POST(Apiurl.EDITPROFILE)
    fun editProfileDetails(@Body request: EditProfileRequest): Observable<EditProfileResponse>


    @POST(Apiurl.CHANGEPASSWORD)
    fun postChangePassword(@Body request: ChangePasswordRequest): Observable<ChangePasswordResponse>




}