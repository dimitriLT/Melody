package com.dimitri.le.torriellec.melody.repository

import com.dimitri.le.torriellec.melody.domain.AlbumRepository
import com.dimitri.le.torriellec.melody.domain.model.Album
import com.dimitri.le.torriellec.melody.repository.mapper.AlbumMapper
import com.dimitri.le.torriellec.melody.repository.network.NetworkException
import io.reactivex.Single
import retrofit2.HttpException

class AlbumRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val albumMapper: AlbumMapper
) : AlbumRepository {
    override fun getAlbums(): Single<List<Album>> {
        try {
            return apiDataSource.getAlbums().map { albumsApi ->
                albumsApi.map { albumApi ->
                    albumMapper.toDomain(albumApi)
                }
            }
        } catch (e: HttpException) {
            throw NetworkException(e)
        }
    }
}