package me.hackathon.monicershopsapp.vo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.util.Locale

data class Wallet(

        @SerializedName("address")
        val address: Int,

        @SerializedName("balance")
        var balance: Double,

        @SerializedName("owner")
        var owner: Owner
)

class TransactionEntity {

        @SerializedName("is_shop")
        @Expose
        var is_shop: Boolean? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("cash_back_percent")
        @Expose
        var cash_back_percent: Int? = null

}

class Transaction {

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("from")
        @Expose
        var from: TransactionEntity? = null
        @SerializedName("to")
        @Expose
        var to: TransactionEntity? = null
        @SerializedName("amount")
        @Expose
        var amount: Double? = null
        @SerializedName("type")
        @Expose
        var type: String? = null
        @SerializedName("created_at")
        @Expose
        var created_at: String? = null

        fun getAmountPretty(): String{
                return String.format(Locale.ITALIAN, "%.2f", amount) + "€"
        }
}

class TransactionResponse {

        @SerializedName("wallet")
        @Expose
        var wallet: Wallet? = null
        @SerializedName("saved_money")
        @Expose
        var saved_money: Double? = null
        @SerializedName("transactions")
        @Expose
        var transactions: List<Transaction>? = null

}