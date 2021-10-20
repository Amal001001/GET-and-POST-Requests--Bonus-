package com.example.getandpostrequestsbonus

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @GET("/custom-people/")
    fun getNames(): Call<ArrayList<peopleItem>?>?

    @POST("/custom-people/")
    fun addName(@Body namesdata:data) : Call<data>
}