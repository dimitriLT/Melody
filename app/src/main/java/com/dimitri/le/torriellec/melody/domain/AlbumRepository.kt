package com.dimitri.le.torriellec.melody.domain

import com.dimitri.le.torriellec.melody.domain.model.Album
import io.reactivex.Single

interface AlbumRepository {

    fun getAlbums(): Single<List<Album>>
}