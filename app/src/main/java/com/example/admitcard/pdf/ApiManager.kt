package com.example.admitcard.pdf

import android.util.Log
import com.example.admitcard.DataItem
import com.example.admitcard.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager {

    fun fetchData(token: String, onDataReceived: (DataItem) -> Unit, onError: (String) -> Unit) {
        val apiService = RetrofitClient.apiService
        val call = apiService.getData("Bearer $token")

        call.enqueue(object : retrofit2.Callback<Model> {
            override fun onResponse(call: Call<Model>, response: retrofit2.Response<Model>) {
                Log.d("ApiManager", "Response received. Code: ${response.code()}")

                if (response.isSuccessful) {
                    val model = response.body()
                    if (model != null && !model.error) {
                        val dataItems = model.data.get(0)
                        Log.d("ApiManager", "Data received: $dataItems")
                        onDataReceived.invoke(dataItems)

                    } else {
                        onError.invoke("Error in API response: ${model?.message ?: "Unknown error"}")
                    }
                } else {
                    onError.invoke("Failed to get data. Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                onError.invoke("Network error occurred: ${t.message}")
            }
        })
    }
}
