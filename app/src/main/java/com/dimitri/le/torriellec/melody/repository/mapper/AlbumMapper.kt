package com.dimitri.le.torriellec.melody.repository.mapper

import com.dimitri.le.torriellec.melody.domain.model.Album
import com.dimitri.le.torriellec.melody.repository.api.model.AlbumApi

class AlbumMapper : BaseDomainMapper<AlbumApi, Album>() {
    override fun toDomain(api: AlbumApi): Album {
        return Album(
            albumId = api.albumId,
            id = api.id,
            title = api.title,
            url = api.url,
            thumbnailUrl = api.thumbnailUrl
        )
    }
}