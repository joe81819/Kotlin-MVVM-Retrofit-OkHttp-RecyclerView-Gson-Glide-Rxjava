package com.example.thirdparty

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.plugins.RxJavaPlugins
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var openDataViewModel: OpenDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        RxJavaPlugins.setErrorHandler { throwable: Throwable? -> Log.d(TAG, "onCreate: error")} // nothing or some logging
        initView()
        connectOpenData()
//        connectOpenDataRX()
//        connectOpenDataRXQuery()
    }

    private fun initView() {
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun connectOpenData() {
        openDataViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(OpenDataViewModel::class.java)
        openDataViewModel.index().observe(this, Observer {
            val openDataChooseList = mutableListOf<OpenData>()
            if (it != null) {
                for (i in 0 until 20) {
                    openDataChooseList.add(it[i])
                }
            }
            Log.d(TAG, "onResponse: openDataChooseList = $openDataChooseList")
            mRecyclerView.adapter = MainAdapter(openDataChooseList, applicationContext)
        })
    }

    private fun connectOpenDataRX() {
        openDataViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(OpenDataViewModel::class.java)
        openDataViewModel.indexRX().observe(this, Observer {
            val openDataChooseList = mutableListOf<OpenData>()
            if (it != null) {
                for (i in 0 until 20) {
                    openDataChooseList.add(it[i])
                }
            }
            Log.d(TAG, "onResponse: openDataChooseList = $openDataChooseList")
            mRecyclerView.adapter = MainAdapter(openDataChooseList, applicationContext)
        })
    }

    private fun connectOpenDataRXQuery() {
        openDataViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(OpenDataViewModel::class.java)
        openDataViewModel.indexRXQuery("QcbUEzN6E6DL").observe(this, Observer {
            val openDataChooseList = mutableListOf<OpenData>()
            if (it != null) {
                for (i in 0 until 20) {
                    openDataChooseList.add(it[i])
                }
            }
            Log.d(TAG, "onResponse: openDataChooseList = $openDataChooseList")
            mRecyclerView.adapter = MainAdapter(openDataChooseList, applicationContext)
        })
    }
}
