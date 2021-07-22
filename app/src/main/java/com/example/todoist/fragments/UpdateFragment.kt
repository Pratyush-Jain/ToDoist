package com.example.todoist.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.todoist.R
import com.example.todoist.adapter.Priority
import com.example.todoist.adapter.Task
import com.example.todoist.data.viewmodel.TaskViewModel

class UpdateFragment : Fragment() {
    lateinit var title:String
    lateinit var priority:String
    lateinit var category:String
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


        val categories = mTaskViewModel.getCategories
        val spinner = view.findViewById<Spinner>(R.id.Updatespinner)
        val spinnerAdater = ArrayAdapter(this.requireContext(),R.layout.support_simple_spinner_dropdown_item, categories)
        spinner.adapter = spinnerAdater
        spinner.setSelection(spinnerAdater.getPosition(category))
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
                Toast.makeText(requireContext(), Priority.valueOf(UpdatedPriority.text.toString()).toString(), Toast.LENGTH_SHORT).show()
                mTaskViewModel.updateTask(updatedTask)
                activity?.onBackPressed()
            }
        }



        return view
    }


}