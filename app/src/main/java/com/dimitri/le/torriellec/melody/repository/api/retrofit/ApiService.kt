package com.dimitri.le.torriellec.melody.repository.api.retrofit

import com.dimitri.le.torriellec.melody.repository.api.model.AlbumApi
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("technical-test.json")
    fun getAlbums(): Single<List<AlbumApi>>

}
