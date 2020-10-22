package com.thell.heroguideapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thell.heroguideapp.helper.retrofit.RetrofitHelper
import com.thell.heroguideapp.response.characters.Characters
import retrofit2.Call
import retrofit2.Response

class ListFragmentViewModel : ViewModel() {

    fun get(limit:Int,offset:Int): LiveData<Characters>
    {
        val characterData: MutableLiveData<Characters> = MutableLiveData()

        val res = RetrofitHelper.Call?.getCharacters(limit = limit.toString(),offset = offset.toString())
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
}