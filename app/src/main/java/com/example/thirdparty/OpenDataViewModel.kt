package com.example.thirdparty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OpenDataViewModel : ViewModel() {
    private val openDataRepository = OpenDataRepository()
    private val openDataResponse = MutableLiveData<List<OpenData>>()

    fun index(): MutableLiveData<List<OpenData>> {
        val result = mutableListOf<OpenData>()
        openDataRepository.run {
            index(object : OnTaskFinish {
                override fun onFinish(data: MutableList<OpenData>) {
                    openDataResponse.postValue(data)
                }
            })
        }
        return openDataResponse
    }

    fun indexRX(): MutableLiveData<List<OpenData>> {
        val result = mutableListOf<OpenData>()
        openDataRepository.run {
            indexRX()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    openDataResponse.setValue(it.body())
                }, {
                    openDataResponse.setValue(result)
                })
        }
        return openDataResponse
    }

    fun indexRXQuery(unitId: String): MutableLiveData<List<OpenData>> {
        val result = mutableListOf<OpenData>()
        openDataRepository.run {
            indexRXQuery(unitId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    openDataResponse.setValue(it.body())
                }, {
                    openDataResponse.setValue(result)
                })
        }
        return openDataResponse
    }
}