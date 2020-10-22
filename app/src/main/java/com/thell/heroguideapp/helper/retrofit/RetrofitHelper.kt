package com.thell.heroguideapp.helper.retrofit

import com.thell.heroguideapp.helper.util.Util.Companion.md5
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

object RetrofitHelper
{
    private var retrofit : Retrofit? = null

    private var  service : RetrofitService? = null

    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private const val  BASE_URL = "https://gateway.marvel.com:443/v1/public/"
    const val  PUBLIC_KEY = "65dbaaea66eed11b77947b5cd1282c67"
    private const val  PRIVATE_KEY = "4ce35462edd44c13f67d7097bb71bb6b6b2398ce"
    const val  TIMESTAMP  = "1"

    fun  getHashKey():String
    {
        val hashString = "$TIMESTAMP$PRIVATE_KEY$PUBLIC_KEY"
        return hashString.md5()
    }

    private var Client : Retrofit? = null
        get()
        {
            if(retrofit == null)
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()

            return retrofit
        }

    var Call : RetrofitService? = null
        get()
        {
            if(service == null)
                service =  Client!!.create(
                    RetrofitService::class.java)

            return service
        }
}