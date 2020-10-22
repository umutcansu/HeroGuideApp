package com.thell.heroguideapp.helper.retrofit

import com.thell.heroguideapp.response.characters.Characters
import com.thell.heroguideapp.response.comics.Comics
import com.thell.heroguideapp.response.comics.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("characters")
    fun getCharacters(
            @Query("ts",encoded = false) ts : String = RetrofitHelper.TIMESTAMP ,
            @Query("apikey",encoded = false) apikey : String = RetrofitHelper.PUBLIC_KEY,
            @Query("hash",encoded = false) hash : String = RetrofitHelper.getHashKey(),
            @Query("limit",encoded = false) limit : String,
            @Query("offset",encoded = false) offset : String): Call<Characters>

    @GET("characters/{id}")
    fun getCharacter(
            @Path("id") id:Int,
            @Query("ts",encoded = false) ts : String = RetrofitHelper.TIMESTAMP,
            @Query("apikey",encoded = false) apikey : String = RetrofitHelper.PUBLIC_KEY,
            @Query("hash",encoded = false) hash : String = RetrofitHelper.getHashKey()
            ): Call<Characters>

    @GET("comics")
    fun getComics(
            @Query("characters") id:Int,
            @Query("startYear") startYear:Int = 2005,
            @Query("orderBy") orderBy:String = "onsaleDate",
            @Query("ts",encoded = false) ts : String = RetrofitHelper.TIMESTAMP,
            @Query("apikey",encoded = false) apikey : String = RetrofitHelper.PUBLIC_KEY,
            @Query("hash",encoded = false) hash : String = RetrofitHelper.getHashKey()
    ): Call<Comics>
}