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

class UpdateFragment : BottomSheetDialogFragment() {
    lateinit var title:String
    lateinit var priority:String
    lateinit var category:String
    lateinit var update_dateBtn:Button
    var id:Long = 0

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title").toString()
        priority = arguments?.getString("priority").toString()
        category = arguments?.getString("categ").toString()
        id = arguments?.getString("id")?.toLong()!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)
        val updateTitle = view.findViewById<EditText>(R.id.Updatetask)
            updateTitle.setText(title)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val priorityRG = view.findViewById<RadioGroup>(R.id.UpdateradioGroup)
        var rbID = priorityRG.checkedRadioButtonId
        var UpdatedPriority = view.findViewById<RadioButton>(rbID)
        update_dateBtn = view.findViewById(R.id.updateDateBtn)

        val categories = mTaskViewModel.getCategories
        val spinner = view.findViewById<Spinner>(R.id.Updatespinner)
        val spinnerAdater = ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item, categories)
        spinner.adapter = spinnerAdater
        spinner.setSelection(spinnerAdater.getPosition(category))

        val cv = view.findViewById<CardView>(R.id.cardView)
        val cldrv = view.findViewById<CalendarView>(R.id.calendarView)
        cv.visibility = View.GONE
        update_dateBtn.setOnClickListener {
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

        val updateBtn = view.findViewById<Button>(R.id.UpdateBtn)

        updateBtn.setOnClickListener {
            if(TextUtils.isEmpty(updateTitle.text)){
                Toast.makeText(requireContext(), "Add title", Toast.LENGTH_SHORT).show()
            }
            else{
                rbID = priorityRG.checkedRadioButtonId
                UpdatedPriority = view.findViewById(rbID)

                val updatedTask = Task(id,updateTitle.text.toString(),
                    spinner.selectedItem.toString(),false,Priority.valueOf(UpdatedPriority.text.toString()))

                mTaskViewModel.updateTask(updatedTask)
                activity?.onBackPressed()
            }
        }



        return view
    }


}