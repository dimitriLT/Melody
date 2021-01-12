package com.dimitri.le.torriellec.melody.domain.di

import com.dimitri.le.torriellec.melody.domain.usecase.GetAlbumUseCase
import org.koin.dsl.module

val domainUseCaseModule = module {
    factory {
        GetAlbumUseCase(
            repository = get()
        )
    }
}

val koinDomainModules = listOf(
    domainUseCaseModule
)