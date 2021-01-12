package com.dimitri.le.torriellec.melody.repository.api

import com.dimitri.le.torriellec.melody.repository.ApiDataSource
import com.dimitri.le.torriellec.melody.repository.api.model.AlbumApi
import com.dimitri.le.torriellec.melody.repository.api.retrofit.ApiService
import io.reactivex.Single

class ApiDataSourceImpl(
    private val apiService: ApiService
) : ApiDataSource {

    override fun getAlbums(): Single<List<AlbumApi>> {
        return apiService.getAlbums()
    }

}