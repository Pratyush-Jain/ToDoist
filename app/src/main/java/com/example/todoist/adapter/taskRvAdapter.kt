package com.example.todoist.adapter

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.todoist.R
import com.example.todoist.data.viewmodel.TaskViewModel
import com.example.todoist.fragments.UpdateFragment


class taskRvAdapter: RecyclerView.Adapter<taskRvAdapter.ViewHolder>() {
    //var Alltasks = emptyList<Task>()
    var Alltasks:MutableList<Task> = mutableListOf()
    lateinit var ccontext:Context
    lateinit var mtaskVM:TaskViewModel

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var taskTitle = itemView.findViewById<TextView>(R.id.task_tv)
        var checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
        var ccv = itemView.findViewById<ConstraintLayout>(R.id.ccv)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_row_item, parent, false)
        ccontext = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return  Alltasks.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mtaskVM = ViewModelProvider(recyclerView.context as ViewModelStoreOwner).get(TaskViewModel::class.java)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun setCheckBoxColor(color:Int){
            holder.checkBox.buttonTintList = getColorStateList(ccontext,color)
        }
        fun UpdateTaskCompletion(id:Long, isComplete: Boolean){
            mtaskVM.UpdateTaskCompletion(id,isComplete)
        }

        holder.taskTitle.text = Alltasks[position].title
        holder.checkBox.isChecked = Alltasks[position].isCompleted

        when(holder.checkBox.isChecked){
            true ->{
                holder.taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.taskTitle.setTextColor(
                    ContextCompat.getColor(
                        ccontext,
                        R.color.blue_button_hint
                    )
                )}
            false->{
                holder.taskTitle.paintFlags = 0
                holder.taskTitle.setTextColor(
                    ContextCompat.getColor(
                        ccontext,
                        R.color.white
                    )
                )}
        }

        when(Alltasks[position].priority){
            Priority.HIGH,Priority.High ->setCheckBoxColor(R.color.PRIORITY_HIGH)
            Priority.MEDIUM,Priority.Medium ->setCheckBoxColor(R.color.PRIORITY_MEDIUM)
            Priority.LOW,Priority.Low ->setCheckBoxColor(R.color.PRIORITY_LOW)
        }

    // Complete task click listener
        holder.checkBox.setOnClickListener{
            UpdateTaskCompletion(Alltasks[position].id,holder.checkBox.isChecked)
            if(holder.checkBox.isChecked)  {
                holder.taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.taskTitle.setTextColor(
                    ContextCompat.getColor(
                        ccontext,
                        R.color.blue_button_hint
                    )
                )
            }
            else{
                holder.taskTitle.paintFlags = Paint.ANTI_ALIAS_FLAG
                holder.taskTitle.setTextColor(
                    ContextCompat.getColor(
                        ccontext,
                        R.color.white
                    )
                )

            }


        }

    // Edit task click listener
        holder.ccv.setOnClickListener {

            val f:Fragment= UpdateFragment()
            sendTaskDataToIntent(holder,position,f)
            val activity  = it.context as? AppCompatActivity
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack(UpdateFragment::class.toString())
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fr, f)
                ?.commit()

        }
    }



    private fun sendTaskDataToIntent(holder: ViewHolder, position: Int,f:Fragment) {
        val bundle = Bundle()
        bundle.putString("title", holder.taskTitle.text.toString())
        bundle.putString("priority", Alltasks[position].priority.toString())
        bundle.putString("categ", Alltasks[position].category)
        bundle.putString("id", Alltasks[position].id.toString())
        f.arguments = bundle

    }

    fun setdata(task: MutableList<Task>){

        this.Alltasks =task
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int): Task {
        val r = Alltasks[position]
        Alltasks.removeAt(position)
        notifyItemRemoved(position)
        return r

    }
}