package com.example.rentacar.model

data class InputField(
    val label:String,
    var value:String,
    val inputType:Int,
    val isDatePicker:Boolean=false
)
