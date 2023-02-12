package com.omdb.movie.main.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.recyclerview.widget.GridLayoutManager
import com.omdb.movie.R
import com.omdb.movie.data.domain.Movie
import com.omdb.movie.databinding.FragmentHomeBinding
import com.omdb.movie.main.adapter.MovieResultPagingAdapter
import com.omdb.movie.main.adapter.LoadStateAdapter
import com.omdb.movie.main.viewmodel.HomeViewModel
import com.omdb.movie.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private var adapter = MovieResultPagingAdapter(::onItemClicked)
    private var searchItem: MenuItem? = null
    private val onQueryListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
                viewModel.searchKeyword(query)
                searchItem?.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String) = true
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setUpEvent()
        observeData()
    }

    private fun initView() {
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        binding.toolbar.title = getString(R.string.app_name)

        val gridLayoutManager = GridLayoutManager(
            requireContext(),
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        val footerAdapter = LoadStateAdapter { adapter.retry() }
        binding.recyclerView.adapter = adapter.withLoadStateFooter(footer = footerAdapter)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =
                if ((position == adapter.itemCount) && footerAdapter.itemCount > 0) 2 else 1
        }
        binding.recyclerView.layoutManager = gridLayoutManager
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        searchItem = menu.findItem(R.id.action_search)
        (searchItem?.actionView as? SearchView)?.apply {
            setOnQueryTextListener(onQueryListener)
        }
    }


    private fun setUpEvent() {
        binding.swipeRefresh.setOnRefreshListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.home_cta_refresh),
                Toast.LENGTH_SHORT
            ).show()
            adapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
            viewModel.refresh()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviePagingData.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        adapter.loadStateFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { loadState ->
                if (loadState.refresh is LoadState.NotLoading) {
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    } else {
                        binding.progressBar.isVisible = false
                    }
                } else {
                    binding.progressBar.isVisible = adapter.snapshot().isEmpty()
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.numberOfResultState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { number ->
                when (number) {
                    null -> {
                        binding.textEmpty.isVisible = false
                        binding.toolbar.subtitle = ""
                    }
                    0 -> {
                        binding.textGuideline.isVisible = false
                        binding.textEmpty.isVisible = true
                        binding.toolbar.subtitle = getString(R.string.home_no_result)
                    }
                    else -> {
                        binding.textGuideline.isVisible = false
                        binding.textEmpty.isVisible = false
                        binding.toolbar.subtitle = getString(R.string.home_number_of_result, number.toString())
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.keywordSearchState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { keyword ->
                if (keyword != null) {
                    binding.toolbar.title = getString(R.string.home_search_keyword, keyword)
                } else {
                    binding.toolbar.title = getString(R.string.app_name)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)


    }

    private fun onItemClicked(item: Movie) {
        // TODO: Go to movie detail screen
    }
}
