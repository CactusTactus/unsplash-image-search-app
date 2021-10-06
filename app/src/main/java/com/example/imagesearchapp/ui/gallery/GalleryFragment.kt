package com.example.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.imagesearchapp.R
import com.example.imagesearchapp.data.UnsplashPhoto
import com.example.imagesearchapp.databinding.FooterUnsplashPhotoLoadStateBinding
import com.example.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment(), UnsplashPhotoAdapter.OnItemClickListener {
    private val viewModel: GalleryViewModel by viewModels()

    private var binding: FragmentGalleryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val unsplashPhotoAdapter = UnsplashPhotoAdapter(this)

        binding!!.apply {
            recyclerViewPhotos.setHasFixedSize(true)
            recyclerViewPhotos.itemAnimator = null
            recyclerViewPhotos.adapter = unsplashPhotoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { unsplashPhotoAdapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { unsplashPhotoAdapter.retry() }
            )
            buttonRetry.setOnClickListener { unsplashPhotoAdapter.retry() }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.photos.collectLatest { photo ->
                unsplashPhotoAdapter.submitData(photo)
            }
        }

        unsplashPhotoAdapter.addLoadStateListener { loadState ->
            binding!!.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewPhotos.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewErrorLoadingResult.isVisible = loadState.source.refresh is LoadState.Error

                // Empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    unsplashPhotoAdapter.itemCount < 1
                ) {
                    recyclerViewPhotos.isVisible = false
                    textViewEmptyResult.isVisible = true
                } else {
                    textViewEmptyResult.isVisible = false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.getItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding!!.recyclerViewPhotos.scrollToPosition(0)
                viewModel.searchPhotos(query ?: "")
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }
}