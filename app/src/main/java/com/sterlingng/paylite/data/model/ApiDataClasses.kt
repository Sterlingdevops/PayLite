package com.sterlingng.paylite.data.model

data class SignUp(
        val email: String,
        val firstname: String,
        val lastname: String,
        val pwd: String,
        val mobile: String,
        val currency: String,
        val bvn: String,
        val dob: String,
        val longitude: String,
        val latitude: String,
        val industryid: String,
        val statusflag: String,
        val safe_zone_flag: String,
        val safe_zone_date_activated: String
)

data class Login(
        val username: String,
        val pwd: String
)