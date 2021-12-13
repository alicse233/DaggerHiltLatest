package com.example.daggerlatestversion.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerlatestversion.R
import com.example.daggerlatestversion.data.User
import com.example.daggerlatestversion.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    lateinit var rv: RecyclerView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById<RecyclerView>(R.id.recyclerView)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        setupUi()
        setupObservers()
    }

    private fun setupUi() {
        rv.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        rv.addItemDecoration(
            DividerItemDecoration(
                rv.context,
                (rv.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.user.observe(this, Observer {
            when (it.status) {
                Status.EMPTY -> progressBar.isVisible = false
                Status.LOADING -> progressBar.isVisible = true
                Status.ERROR -> {
                    progressBar.isVisible = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    progressBar.isVisible = false
                    it.data?.let {
                        renderList(it)
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}