package com.example.todoist.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todoist.R
import com.example.todoist.adapter.Category
import com.example.todoist.data.viewmodel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CategoryFragment : BottomSheetDialogFragment() {

    private lateinit var mTaskViewModel: TaskViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_category, container, false)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        view.findViewById<Button>(R.id.addCategorybtn).setOnClickListener{
            insertCategoryToDatabase(view)
        }
        return view
    }

    private fun insertCategoryToDatabase(view: View) {
        val categName = view.findViewById<EditText>(R.id.CategorynameET).text
        if (TextUtils.isEmpty(categName)){
            Toast.makeText(requireContext(), "Add category title", Toast.LENGTH_SHORT).show()
        }else if(categName.length>20){
                Toast.makeText(requireContext(), "Max 20 Characters only", Toast.LENGTH_SHORT).show()
            }
            else {
                val categ = Category(categName.toString())
                mTaskViewModel.addCategory(categ)
                categName.clear()
                activity?.onBackPressed()
            }

    }


}