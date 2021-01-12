package com.dimitri.le.torriellec.melody.domain

import com.dimitri.le.torriellec.melody.domain.model.Album
import com.dimitri.le.torriellec.melody.domain.usecase.GetAlbumUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class AlbumUseCaseTest {

    private lateinit var useCase: GetAlbumUseCase
    private val repository = mockk<AlbumRepository>()

    @Before
    fun setUp() {
        useCase = GetAlbumUseCase(repository)
    }

    @Test
    fun `when load should get albums`() {

        val albums = listOf(
            Album(
                albumId = 1,
                id = 1,
                title = "accusamus beatae ad facilis cum similique qui sunt",
                url = "https://via.placeholder.com/600/92c952",
                thumbnailUrl = "https://via.placeholder.com/150/92c952"
            ),
            Album(
                albumId = 1,
                id = 2,
                title = "reprehenderit est deserunt velit ipsam",
                url = "https://via.placeholder.com/600/771796",
                thumbnailUrl = "https://via.placeholder.com/150/771796"
            )
        )

        coEvery { repository.getAlbums() } returns Single.just(albums)

        val result = useCase.invoke()

        val testObserver = TestObserver<List<Album>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val listResult = testObserver.values()[0]
        Assert.assertNotNull(listResult)
        assert(listResult is List<Album>)
        assertThat(listResult, `is`(albums))
    }

    @Test
    fun `when load should get empty list`() {
        val albums = emptyList<Album>()

        coEvery { repository.getAlbums() } returns Single.just(albums)

        val result = useCase.invoke()

        val testObserver = TestObserver<List<Album>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val listResult = testObserver.values()[0]
        Assert.assertNotNull(listResult)
        assert(listResult is List<Album>)
        assertThat(listResult, `is`(albums))
    }

    @Test
    fun `when load throws an exception`() {

        coEvery { repository.getAlbums() } throws HttpException(
            Response.error<Any>(
                500,
                "Mock Server Error".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )

        try {
            useCase.invoke()
        } catch (e: Exception) {
            assert(e is HttpException)
        }

    }
}