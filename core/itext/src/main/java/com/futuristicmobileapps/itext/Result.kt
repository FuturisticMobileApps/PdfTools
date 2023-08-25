package com.futuristicmobileapps.itext

data class Result(
    var message : String = "Something went wrong!",
    var numberOfPages : Int = 0,
    var fileName : String? = null,
    var status : Status = Status.FAILURE
)
