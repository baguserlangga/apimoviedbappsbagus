package com.example.apimoviedbappsbagus.Model

import com.google.gson.annotations.SerializedName

class ProductionCountries (
    @SerializedName("iso_3166_1" ) var iso31661 : String? = null,
    @SerializedName("name"       ) var name     : String? = null

)