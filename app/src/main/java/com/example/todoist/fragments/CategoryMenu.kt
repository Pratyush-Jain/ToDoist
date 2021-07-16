package com.example.todoist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoist.R
import com.example.todoist.adapter.AllCategRvAdapter
import com.example.todoist.data.viewmodel.TaskViewModel

class CategoryMenu :Fragment(R.layout.fragment_category_menu) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_category_menu, container, false)

        var AllcategRV = view.findViewById<RecyclerView>(R.id.AllCategRV)
        var AllcategAdapter = AllCategRvAdapter()
        AllcategRV.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = AllcategAdapter
            hasFixedSize()
        }
        mTaskViewModel.getAllCategory.observe(this, Observer {
            AllcategAdapter.setdata(it)
        })
        return view
    }
}