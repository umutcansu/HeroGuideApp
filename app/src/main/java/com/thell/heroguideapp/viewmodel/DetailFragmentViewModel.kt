package com.thell.heroguideapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thell.heroguideapp.helper.retrofit.RetrofitHelper
import com.thell.heroguideapp.response.characters.Characters
import com.thell.heroguideapp.response.comics.Comics
import com.thell.heroguideapp.response.comics.Result
import retrofit2.Call
import retrofit2.Response

class DetailFragmentViewModel : ViewModel() {

    fun get(id:Int): MutableLiveData<Characters>
    {
        val characterData: MutableLiveData<Characters> = MutableLiveData()

        val res = RetrofitHelper.Call?.getCharacter(id = id)
        res?.enqueue(object : retrofit2.Callback<Characters> {
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                if (response.isSuccessful) {
                    characterData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                characterData.value = null
            }

        })
        return characterData
    }

    fun getComics(id:Int): MutableLiveData<Comics>
    {
        val characterData: MutableLiveData<Comics> = MutableLiveData()

        val res = RetrofitHelper.Call?.getComics(id = id)
        res?.enqueue(object : retrofit2.Callback<Comics> {
            override fun onResponse(call: Call<Comics>, response: Response<Comics>) {
                if (response.isSuccessful) {
                    characterData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Comics>, t: Throwable) {
                characterData.value = null
            }

        })
        return characterData
    }
}