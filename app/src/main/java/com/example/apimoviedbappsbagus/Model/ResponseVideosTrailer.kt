package com.example.apimoviedbappsbagus.Model

import com.google.gson.annotations.SerializedName

data class ResponseVideosTrailer (
    @SerializedName("id"      ) var id      : Int?               =null,
    @SerializedName("results" ) var trailer : ArrayList<TrailerModel> = arrayListOf()

)
