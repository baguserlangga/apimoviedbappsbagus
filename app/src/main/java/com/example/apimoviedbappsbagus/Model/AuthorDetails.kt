package com.example.apimoviedbappsbagus.Model

import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    @SerializedName("name"        ) var name       : String? = null,
    @SerializedName("username"    ) var username   : String? = null,
    @SerializedName("avatar_path" ) var avatarPath : String? = null,
    @SerializedName("rating"      ) var rating     : Int?    = null
)
