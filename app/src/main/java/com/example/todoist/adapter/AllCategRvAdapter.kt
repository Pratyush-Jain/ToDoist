package com.example.todoist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoist.POJO.CategTaskCount
import com.example.todoist.R

class AllCategRvAdapter(private val DeletebtnClickCallback: ((String) -> Unit)?): RecyclerView.Adapter<AllCategRvAdapter.ViewHolder>()  {
    var data = emptyList<CategTaskCount>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var categ = itemView.findViewById<TextView>(R.id.categ_tv)
        var dltCateg = itemView.findViewById<ImageButton>(R.id.categ_delete )

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_categ_item_row_rv,parent,false)
        return AllCategRvAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categ.text = data[position].category
        holder.dltCateg.setOnClickListener {
            DeletebtnClickCallback?.invoke(holder.categ.text.toString())

        }
    }

    override fun getItemCount(): Int = data.size

    fun setdata(adata:List<CategTaskCount>){
        this.data = adata
        notifyDataSetChanged()
    }
}