package com.example.todoist.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todoist.R
import com.example.todoist.adapter.Priority
import com.example.todoist.adapter.Task
import com.example.todoist.data.viewmodel.TaskViewModel


class AddFragment : Fragment() {
    private lateinit var mTaskViewModel: TaskViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val SelectedCateg = arguments?.getString("SelectedCateg")
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val categories = mTaskViewModel.getCategories

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val spinnerAdater = ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item, categories)
        spinner.adapter = spinnerAdater
        //Toast.makeText(requireContext(),SelectedCateg, Toast.LENGTH_SHORT).show()
        spinner.setSelection(spinnerAdater.getPosition(SelectedCateg))
        view.findViewById<Button>(R.id.addBtn).setOnClickListener{
            insertDataToDatabase(view,spinner.selectedItem.toString())
        }
        return view
    }

    private fun insertDataToDatabase(view: View, category: String) {

        val task = view.findViewById<EditText>(R.id.task).text
        val priorityRG = view.findViewById<RadioGroup>(R.id.radioGroup)
        var rbID = priorityRG.checkedRadioButtonId
        var apriority = view.findViewById<RadioButton>(rbID).text.toString()

        if(!TextUtils.isEmpty(task)) {
            val taska = Task(0,task.toString(), category, false,Priority.valueOf(apriority))
            mTaskViewModel.addTask(taska)

            activity?.onBackPressed()
        }else{
            Toast.makeText(view.context, "Add title", Toast.LENGTH_SHORT).show()
        }



    }
}