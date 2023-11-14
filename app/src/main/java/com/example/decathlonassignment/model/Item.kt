package com.example.decathlonassignment.model

data class Item(
    val name : String ?= null ,
    val id : Int ?= null ,
    val price : Float ?= null ,
    val imageUrl : String ?= null,
    val brand : String ?= null
)