package com.app.travel.app


import android.util.Log
import com.app.travel.BuildConfig
import com.app.travel.app.Apiurl.BASE_URL
import com.app.travel.apputlis.UserInfo
//import com.elarkgroup.gwcl.api.Apiurl.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class Inceptor : Interceptor {
    @Volatile
    private var host: String? = null
    internal var token: String? = null


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()



        when {
            UserInfo.loginStatus -> {
                request = request.newBuilder()
                    .header("Authorization", UserInfo.token)
                    .build()
            }
            else -> {
                request = request.newBuilder()

                    .build()
            }
        }


        val response = chain.proceed(request)
        if (response.code() == 402) {
            response.close()
            return response
        }
        return response
    }

}