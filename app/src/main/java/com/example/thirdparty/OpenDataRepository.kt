package com.example.thirdparty

import android.util.Log
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenDataRepository {
    private val TAG = OpenDataRepository::class.java.simpleName
    private val apiService = AppClientManager.client.create(ApiService::class.java)

    fun index(task: OnTaskFinish) {
        apiService.index().enqueue(object : Callback<List<OpenData>> {
            override fun onResponse(
                call: Call<List<OpenData>>,
                response: Response<List<OpenData>>
            ) {
                val list = response.body()
                val openDataChooseList = mutableListOf<OpenData>()
                if (list != null) {
                    for (i in 0 until 20) {
                        openDataChooseList.add(list[i])
                    }
                }
                Log.d(TAG, "onResponse: openDataChooseList = $openDataChooseList")
                task.onFinish(openDataChooseList)
            }

            override fun onFailure(call: Call<List<OpenData>>, t: Throwable) {

            }
        })
    }

    fun indexRX(): Single<Response<List<OpenData>>> {
        return apiService.indexRX()
    }

    fun indexRXQuery(unitId: String): Single<Response<List<OpenData>>> {
        return apiService.indexRXQuery(unitId)
    }
}