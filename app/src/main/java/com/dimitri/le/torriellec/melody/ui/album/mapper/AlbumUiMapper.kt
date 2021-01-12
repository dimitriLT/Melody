package com.dimitri.le.torriellec.melody.ui.album.mapper

import com.dimitri.le.torriellec.melody.domain.model.Album
import com.dimitri.le.torriellec.melody.ui.album.model.AlbumUi
import com.dimitri.le.torriellec.melody.ui.album.model.AlbumsUi
import com.dimitri.le.torriellec.melody.ui.common.BaseUiMapper

class AlbumUiMapper : BaseUiMapper<List<Album>, AlbumsUi>() {
    override fun toUi(domain: List<Album>): AlbumsUi {
        val albumsUi = domain.map { album ->
            AlbumUi(
                albumId = album.albumId,
                id = album.id,
                url = album.url,
                title = album.title,
                thumbnailUrl = album.thumbnailUrl
            )
        }
        return AlbumsUi(
            albums = albumsUi
        )
    }
}