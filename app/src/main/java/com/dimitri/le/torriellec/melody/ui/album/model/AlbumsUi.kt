package com.dimitri.le.torriellec.melody.ui.album.model

data class AlbumsUi(
    val albums: List<AlbumUi>
)

data class AlbumUi(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)