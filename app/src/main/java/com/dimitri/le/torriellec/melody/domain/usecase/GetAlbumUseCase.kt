package com.dimitri.le.torriellec.melody.domain.usecase

import com.dimitri.le.torriellec.melody.domain.AlbumRepository
import com.dimitri.le.torriellec.melody.domain.model.Album
import io.reactivex.Single

class GetAlbumUseCase(
    private val repository: AlbumRepository
) {

    operator fun invoke(): Single<List<Album>> {
        return repository.getAlbums()
    }
}