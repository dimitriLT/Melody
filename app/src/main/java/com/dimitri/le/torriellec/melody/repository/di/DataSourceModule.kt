package com.dimitri.le.torriellec.melody.repository.di

import com.dimitri.le.torriellec.melody.repository.AlbumRepositoryImpl
import com.dimitri.le.torriellec.melody.repository.api.ApiDataSourceImpl
import com.dimitri.le.torriellec.melody.repository.api.retrofit.ApiRetrofitFactory
import com.dimitri.le.torriellec.melody.repository.mapper.AlbumMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val apiRetrofitModule = module {
    single {
        ApiRetrofitFactory(androidContext()).buildApiService(
            ApiRetrofitFactory(androidContext()).buildApiRetrofit()
        )
    }
}

val apiDataSourceModule = module {
    single {
        ApiDataSourceImpl(
            apiService = get()
        )
    }
}

val apiMapperModule = module {
    single {
        AlbumMapper()
    }
}

val repositoryModule = module {
    single {
        AlbumRepositoryImpl(
            apiDataSource = get(),
            albumMapper = get()
        )
    }
}

val koinDataSourceModules = listOf(
    apiRetrofitModule,
    apiDataSourceModule,
    apiMapperModule,
    repositoryModule
)