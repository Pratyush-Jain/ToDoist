package com.example.todoist.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todoist.fragments.CategoryFragment
import com.example.todoist.R
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.todoist.POJO.CategTaskCount


class categoriesRvAdapter(var activity: Activity,private val itemClickCallback: ((String) -> Unit)?):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: List<CategTaskCount> = emptyList()
    val  CATEGORY = 1
    val selection =0
    val  ADD_CATEGORY = 0
    var CardViewlist: MutableList<CardView> = mutableListOf()
    lateinit var vview:View


    lateinit var ccontext:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        ccontext = parent.context
        if(viewType ==ADD_CATEGORY){
            var view = LayoutInflater.from(parent.context).inflate(
                R.layout.add_category,
                parent,
                false
            )
            vview = view

            return AddCategoryViewHolder(view)
        }
        else{
            var view = LayoutInflater.from(parent.context).inflate(
                R.layout.category_item,
                parent,
                false
            )
            return CategoryViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        if(holder is CategoryViewHolder){

            var categoryHolder: CategoryViewHolder = holder
            CardViewlist.add(holder.cardView)
            categoryHolder.categoryName.text = data[position].category
            //categoryHolder.totalTask.text = data[position].count.toString()
            categoryHolder.cardView.setOnClickListener{
                itemClickCallback?.invoke(holder.categoryName.text.toString())
            for(cardView in CardViewlist){
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                cardView.findViewById<TextView>(R.id.Category_tv).setTextColor(Color.parseColor("#FF993A"))
            }

            holder.cardView.setCardBackgroundColor(Color.parseColor("#FF993A"))
            holder.categoryName.setTextColor(Color.parseColor("#FFFFFF"))
            }
            if(position ==0){
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FF993A"))
                holder.categoryName.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
        else if(holder is AddCategoryViewHolder){
                holder.add_categ_view.setOnClickListener{
                    vview.findViewById<CardView>(R.id.add_category_cv).setOnClickListener {

                        val categoryBSF = CategoryFragment()
                        val act = it.context as? AppCompatActivity
                        if (act != null) {
                            categoryBSF.show(act.supportFragmentManager, "categoryBSF")
                        }
                        categoryBSF.setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogStyle)
//                        act.supportFragmentManager.beginTransaction()
//                            .add(R.id.fr,f)
//                            .addToBackStack("fragment")
//                            .commit()
                    }

                }
            }
        }


    override fun getItemCount(): Int {
        return data.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == data.size){
            ADD_CATEGORY
        }
        else{
            CATEGORY
        }
    }

    class AddCategoryViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var add_categ_view = itemView.findViewById<CardView>(R.id.add_category_cv)
    }

    class CategoryViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var cardView:CardView = itemView.findViewById(R.id.category_cv)
        var categoryName = itemView.findViewById<TextView>(R.id.Category_tv)
        var totalTask = itemView.findViewById<TextView>(R.id.total_task_tv)

    }
    fun setCateg(category:List<CategTaskCount>){
        this.data =category
        notifyDataSetChanged()
    }


}