package com.example.apimoviedbappsbagus.Model

import com.google.gson.annotations.SerializedName

data class ResponseGenres
    (
    @SerializedName("genres" ) var genres : ArrayList<GenresModel> = arrayListOf()

)