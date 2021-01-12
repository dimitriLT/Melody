package com.dimitri.le.torriellec.melody.ui.album

import android.util.Log
import com.dimitri.le.torriellec.melody.domain.usecase.GetAlbumUseCase
import com.dimitri.le.torriellec.melody.ui.album.mapper.AlbumUiMapper
import com.dimitri.le.torriellec.melody.ui.album.model.AlbumsUi
import com.dimitri.le.torriellec.melody.ui.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class AlbumsViewModel(
    getAlbumUseCase: GetAlbumUseCase,
    albumUiMapper: AlbumUiMapper
) : BaseViewModel<AlbumsUi>() {

    private val disposable: Disposable

    init {

        val compositeDisposable = CompositeDisposable()

        disposable = getAlbumUseCase()
            .subscribeOn(Schedulers.io())
            .map { albums ->
                albumUiMapper.toUi(albums)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { albumsUi ->
                    postUiData(albumsUi)
                },
                onError = {
                    Log.e(TAG, "error when getting albums", it)
                }
            )

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    companion object {
        private val TAG = AlbumsViewModel::class.java.simpleName
    }
}