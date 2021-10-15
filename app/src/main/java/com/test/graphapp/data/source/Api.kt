package com.test.graphapp.data.source

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("dataset_cpu_mem.json")
    fun getCPUStatistics(): Single<Response<ResponseBody>>
}