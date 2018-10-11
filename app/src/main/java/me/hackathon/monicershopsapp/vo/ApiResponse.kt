package me.hackathon.monicershopsapp.vo

import okhttp3.Headers
import retrofit2.Response
import java.io.IOException

class ApiResponse<T> {
    private val code: Int
    val body: T?
    val errorMessage: String
    val headers: Headers?

    val isSuccessful: Boolean
        get() = code in 200..299

    constructor(error: Throwable) {
        code = 1000
        body = null
        headers = null
        errorMessage = error.message.toString()
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            headers = response.headers()
            errorMessage = ""
        } else {
            var message = ""
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {

                }
            }
            if (message.trim { it <= ' ' }.isEmpty()) {
                message = response.message()
            }
            errorMessage = message
            body = null
            headers = null
        }
    }
}
