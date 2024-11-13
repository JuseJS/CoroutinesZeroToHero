package org.iesharia.coroutineszerotohero

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    suspend fun getUsers(): Response<List<UserDataResponse>>
}
