package me.hackathon.monicershopsapp.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location {

  @SerializedName("lat")
  @Expose
  var lat: String? = null
  @SerializedName("lng")
  @Expose
  var lng: String? = null

}

class Shop {

  @SerializedName("id")
  @Expose
  var id: Int? = null
  @SerializedName("name")
  @Expose
  var name: String? = null
  @SerializedName("discount")
  @Expose
  var discount: Int? = null
  @SerializedName("address")
  @Expose
  var address: String? = null
  @SerializedName("location")
  @Expose
  var location: Location? = null

}