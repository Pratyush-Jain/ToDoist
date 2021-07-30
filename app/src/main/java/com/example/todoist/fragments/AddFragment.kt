package com.example.todoist.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.todoist.R
import com.example.todoist.adapter.Priority
import com.example.todoist.adapter.Task
import com.example.todoist.data.viewmodel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class AddFragment() : BottomSheetDialogFragment() {

    private lateinit var mTaskViewModel: TaskViewModel
    lateinit var dateBtn:Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val SelectedCateg = arguments?.getString("SelectedCateg")
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val categories = mTaskViewModel.getCategories
        dateBtn = view.findViewById(R.id.DateBtn)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val spinnerAdater = ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item, categories)
        spinner.adapter = spinnerAdater
        //Toast.makeText(requireContext(),SelectedCateg, Toast.LENGTH_SHORT).show()
        spinner.setSelection(spinnerAdater.getPosition(SelectedCateg))
        val cv = view.findViewById<CardView>(R.id.cardView)
        val cldrv = view.findViewById<CalendarView>(R.id.calendarView)
        cv.visibility = View.GONE
        dateBtn.setOnClickListener {
            //activity?.onBackPressed()
            val timeLong = Calendar.getInstance().timeInMillis
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
            cv.visibility = View.VISIBLE
            cldrv.apply {
                date = timeLong
                minDate= timeLong-1000
            }
            view.findViewById<TextView>(R.id.cancel_cv).setOnClickListener {
                cv.visibility = View.GONE
            }
        }

        view.findViewById<Button>(R.id.addBtn).setOnClickListener{
            var spinnerCateg = spinner.selectedItem?.toString()
            if(spinnerCateg.isNullOrEmpty()){
                Toast.makeText(view.context, "Add Category", Toast.LENGTH_SHORT).show()
            }
            else {
                insertDataToDatabase(view,spinnerCateg)
            }
        }
        return view
    }

    private fun insertDataToDatabase(view: View, category: String) {

        val task = view.findViewById<EditText>(R.id.task)
        val priorityRG = view.findViewById<RadioGroup>(R.id.radioGroup)
        var rbID = priorityRG.checkedRadioButtonId
        var apriority = view.findViewById<RadioButton>(rbID).text.toString()

        if(!TextUtils.isEmpty(task.text)) {
            val taska = Task(0,task.text.toString(), category, false,Priority.valueOf(apriority))
            mTaskViewModel.addTask(taska)

//            activity?.onBackPressed()
        }else{
            Toast.makeText(view.context, "Add title", Toast.LENGTH_SHORT).show()
        }



    }
}