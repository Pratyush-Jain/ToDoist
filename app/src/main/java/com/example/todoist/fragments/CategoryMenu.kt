package com.example.todoist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        var AllcategAdapter = AllCategRvAdapter(DeletebtnClickCallback = fun(categ: String) {
            if(mTaskViewModel.getCategories.size>1){
                mTaskViewModel.DeleteTaskWithCategory(categ)
                Toast.makeText(requireContext(), "Category and tasks deleted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(), "Atleast one category required", Toast.LENGTH_SHORT).show()
            }
        })

        AllcategRV.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = AllcategAdapter
            hasFixedSize()
        }
        mTaskViewModel.getAllCategory.observe(viewLifecycleOwner, Observer {
            AllcategAdapter.setdata(it)
        })
        return view
    }
}