package com.dimitri.le.torriellec.melody.ui.di

import com.dimitri.le.torriellec.melody.ui.album.AlbumsViewModel
import com.dimitri.le.torriellec.melody.ui.album.mapper.AlbumUiMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiAlbumsViewModelModule = module {
    single {
        AlbumUiMapper()
    }
    viewModel {
        AlbumsViewModel(
            getAlbumUseCase = get(),
            albumUiMapper = get()
        )
    }
}


val koinUiModules = listOf(
    uiAlbumsViewModelModule
)
