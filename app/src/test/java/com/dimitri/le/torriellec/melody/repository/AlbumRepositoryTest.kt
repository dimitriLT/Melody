package com.dimitri.le.torriellec.melody.repository

import com.dimitri.le.torriellec.melody.domain.AlbumRepository
import com.dimitri.le.torriellec.melody.domain.model.Album
import com.dimitri.le.torriellec.melody.repository.api.model.AlbumApi
import com.dimitri.le.torriellec.melody.repository.mapper.AlbumMapper
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AlbumRepositoryTest {

    private lateinit var repository: AlbumRepository
    private lateinit var apiDataSource: ApiDataSource
    private lateinit var albumMapper: AlbumMapper

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun before() {
        apiDataSource = mockk()
        albumMapper = AlbumMapper()
        repository = AlbumRepositoryImpl(apiDataSource, albumMapper)
    }


    @Test
    fun `when albums are requested, should call apiDataSource and return response `() {

        every { apiDataSource.getAlbums() } returns Single.just(createRepos())

        val result = repository.getAlbums()

        val testObserver = TestObserver<List<Album>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertThat(listResult.size, `is`(2))
        assertThat(listResult[0].title, `is`("accusamus beatae ad facilis cum similique qui sunt"))
        assertThat(listResult[0].url, `is`("https://via.placeholder.com/600/92c952"))
        assertThat(listResult[0].thumbnailUrl, `is`("https://via.placeholder.com/150/92c952"))
        assertThat(listResult[1].title, `is`("reprehenderit est deserunt velit ipsam"))
        assertThat(listResult[1].url, `is`("https://via.placeholder.com/600/771796"))
        assertThat(listResult[1].thumbnailUrl, `is`("https://via.placeholder.com/150/771796"))

    }


    private fun createRepos() = listOf(
        AlbumApi(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        ),
        AlbumApi(
            albumId = 1,
            id = 2,
            title = "reprehenderit est deserunt velit ipsam",
            url = "https://via.placeholder.com/600/771796",
            thumbnailUrl = "https://via.placeholder.com/150/771796"
        ),
    )

    class RxImmediateSchedulerRule : TestRule {

        override fun apply(base: Statement, d: Description): Statement {
            return object : Statement() {
                @Throws(Throwable::class)
                override fun evaluate() {
                    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                    try {
                        base.evaluate()
                    } finally {
                        RxJavaPlugins.reset()
                        RxAndroidPlugins.reset()
                    }
                }
            }
        }
    }
}