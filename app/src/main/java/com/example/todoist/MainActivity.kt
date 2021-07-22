package com.example.todoist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoist.POJO.CategTaskCount
import com.example.todoist.adapter.Category
import com.example.todoist.adapter.Task
import com.example.todoist.adapter.categoriesRvAdapter
import com.example.todoist.adapter.taskRvAdapter
import com.example.todoist.data.viewmodel.TaskViewModel
import com.example.todoist.fragments.AddFragment
import com.example.todoist.fragments.CategoryMenu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var mTaskViewModel: TaskViewModel
    private var Selectcateg = "Personal"



    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        //mTaskViewModel.addCategory(Category(Selectcateg))
        //mTaskViewModel.setCateg("Personal")
        //var categ = emptyList<CategTaskCount>()
//        var allCateories = mTaskViewModel.getCategories
//        if(allCateories.isEmpty()){
//            mTaskViewModel.addCategory(Category(Selectcateg))
//        }
        mTaskViewModel.getAllCategory.observe(this,{
            if(it.isEmpty()){
                mTaskViewModel.addCategory(Category(Selectcateg))
                mTaskViewModel.selectedCateg.value = Selectcateg
            }
            else{
                mTaskViewModel.selectedCateg.value = it[0].category
            }
        })



            //val user = getIntent().getStringExtra("Uname")
        //Toast.makeText(this, "Welcome $user", Toast.LENGTH_SHORT).show()

        val drawer  = findViewById<DrawerLayout>(R.id.drawer_layout)
        val categRV = findViewById<RecyclerView>(R.id.CategoryRV)
        val categAdapter = categoriesRvAdapter(activity = this,itemClickCallback = fun(categ: String) {
            mTaskViewModel.selectedCateg.value = categ
            //Toast.makeText(this, mTaskViewModel.selectedCateg.value, Toast.LENGTH_SHORT).show()
        })
        categRV.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categAdapter
            hasFixedSize()
        }

        toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.categories -> showMenuFragment(CategoryMenu())
//                R.id.analytics -> Toast.makeText(this, "analytics", Toast.LENGTH_SHORT).show()
//                R.id.settings -> Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show()
//                R.id.logout -> Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
            }
            drawer.closeDrawer(Gravity.START)
            true
        }

        val taskRv = findViewById<RecyclerView>(R.id.tasks_rv)
        val rvadapter = taskRvAdapter()
        rvadapter.setdata(mTaskViewModel.readAllTasksNonLive(mTaskViewModel.selectedCateg.value.toString()))
        taskRv.apply{
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = rvadapter
            hasFixedSize()
        }


        // Swipe to delete
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val r = rvadapter.deleteItem(position)
                mTaskViewModel.deleteTask(r)
            }
        }

        // Attach to recycler view
        val itTchHlpr = ItemTouchHelper(itemTouchHelper)
        itTchHlpr.attachToRecyclerView(taskRv)



        mTaskViewModel.readAllTask.observe(this, Observer {
            rvadapter.setdata(it.filter { it.category == mTaskViewModel.selectedCateg.value.toString() } as MutableList<Task>)
        })

        // Update recyclerview on category change
        mTaskViewModel.selectedCateg.observe(this, Observer {
            rvadapter.setdata(mTaskViewModel.readAllTasksNonLive(it))
            Selectcateg = it
        })

        // feed value to category adapter
        mTaskViewModel.getAllCategory.observe(this, Observer {
            categAdapter.setCateg(it)
        })


        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("SelectedCateg", Selectcateg)
            val f:Fragment = AddFragment()
            f.arguments = bundle
            supportFragmentManager.beginTransaction()
                .add(R.id.fr,f)
                .addToBackStack("fragment")
                .commit()
        }

    }



    private fun showMenuFragment(f:Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fr,f)
            .addToBackStack("Category fragment")
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
