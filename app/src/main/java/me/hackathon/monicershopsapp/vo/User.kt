package me.hackathon.monicershopsapp.vo

import com.google.gson.annotations.SerializedName

data class User(

        @SerializedName("wallet")
        val wallet: Wallet,

        @SerializedName("pay_code")
        val payCode: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("id")
        val id: Int,

        @SerializedName("email")
        val email: String,

        @SerializedName("shop")
        val shop: Shop
)