package me.hackathon.monicershopsapp.vo

import com.google.gson.annotations.SerializedName

data class Owner(

        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("email")
        var email: String,

        @SerializedName("pay_code")
        var pay_code: String
)