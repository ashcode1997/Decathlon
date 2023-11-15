package com.example.decathlonassignment.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("name")
    val name : String ?= null ,
    @SerializedName("id")
    val id : Int ?= null ,
    @SerializedName("price")
    val price : Float ?= null ,
    @SerializedName("imageUrl")
    val imageUrl : String ?= null,
    @SerializedName("brand")
    val brand : String ?= null
)