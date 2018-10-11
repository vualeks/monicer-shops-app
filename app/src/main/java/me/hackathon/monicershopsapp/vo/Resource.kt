package me.hackathon.monicershopsapp.vo

import me.hackathon.monicershopsapp.vo.Status.*

class Resource<out T>(val status: Status, val data: T?, val error: NetworkError?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource = other as Resource<*>?

        if (status !== resource!!.status) {
            return false
        }
        if (if (error != null) error != resource!!.error else resource!!.error != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", error='" + error + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }

    val isLoading: Boolean = status == LOADING

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(error: NetworkError, data: T?): Resource<T> {
            return Resource(ERROR, data, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}