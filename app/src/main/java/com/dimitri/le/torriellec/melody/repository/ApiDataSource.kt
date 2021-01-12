package com.dimitri.le.torriellec.melody.repository

import com.dimitri.le.torriellec.melody.repository.api.model.AlbumApi
import io.reactivex.Single

interface ApiDataSource {

    fun getAlbums(): Single<List<AlbumApi>>

}