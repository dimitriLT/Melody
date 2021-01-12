package com.dimitri.le.torriellec.melody.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimitri.le.torriellec.melody.R
import com.dimitri.le.torriellec.melody.ui.album.view.adapter.AlbumsAdapter
import kotlinx.android.synthetic.main.fragment_albums.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsFragment : Fragment() {

    private val viewModel: AlbumsViewModel by viewModel()
    private val recyclerAdapter = AlbumsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albums_recycler.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = recyclerAdapter
        }

        viewModel.uiData.observe(viewLifecycleOwner, { albumsUi ->
            recyclerAdapter.submitList(albumsUi.albums)
        })

    }
}