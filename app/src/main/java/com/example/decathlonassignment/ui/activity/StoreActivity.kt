package com.example.decathlonassignment.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.decathlonassignment.R
import com.example.decathlonassignment.databinding.ActivityStoreBinding
import com.example.decathlonassignment.di.Injection
import com.example.decathlonassignment.ui.adapter.StoreAdapter
import com.example.decathlonassignment.viewmodel.StoreViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.activity.viewModels

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    private lateinit var adapter: StoreAdapter
    private val viewModel by viewModels<StoreViewModel>(
        factoryProducer = { Injection.provideViewModelFactory(owner = this) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_store, null, false)
        initUI()
        initObservers()
        setContentView(binding.root)
    }

    private fun initUI() {
        adapter = StoreAdapter()
        binding.bindAdapter(adapter)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.setSearch(query.orEmpty())
                binding.loader.isVisible = true
                return true
            }
        })
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collectLatest {
                    binding.loader.isVisible = false
                    adapter.submitData(it)
                }

            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    binding.progress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
    }


    private fun ActivityStoreBinding.bindAdapter(storeAdapter: StoreAdapter) {
        list.adapter = storeAdapter
        list.layoutManager = LinearLayoutManager(list.context)
        val decoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
    }
}