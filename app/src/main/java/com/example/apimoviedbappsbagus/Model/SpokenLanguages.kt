package com.example.apimoviedbappsbagus.Model

import com.google.gson.annotations.SerializedName

class SpokenLanguages (

    @SerializedName("english_name" ) var englishName : String? = null,
    @SerializedName("iso_639_1"    ) var iso6391     : String? = null,
    @SerializedName("name"         ) var name        : String? = null

)
