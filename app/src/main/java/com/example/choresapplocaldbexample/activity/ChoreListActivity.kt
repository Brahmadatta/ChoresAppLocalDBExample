package com.example.choresapplocaldbexample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.choresapplocaldbexample.R
import com.example.choresapplocaldbexample.data.ChoreListAdapter
import com.example.choresapplocaldbexample.data.ChoresDatabaseHandler
import com.example.choresapplocaldbexample.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import java.util.ArrayList

class ChoreListActivity : AppCompatActivity() {

    private var adapter : ChoreListAdapter? = null
    private var chorelist : ArrayList<Chore> ?= null
    private var layoutManager : RecyclerView.LayoutManager ?= null
    var databaseHandler : ChoresDatabaseHandler ?= null
    var choreListItems : ArrayList<Chore> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)


        databaseHandler = ChoresDatabaseHandler(this)

        chorelist = ArrayList<Chore>()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        //adapter = ChoreListAdapter(chorelist!!,this)
        adapter = ChoreListAdapter(choreListItems!!,this)

        //set up list - recyclerview
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter


        //Load our chores
        chorelist = databaseHandler!!.readChores()

        for (c in chorelist!!.iterator())
        {
             val chore = Chore()
            chore.choreName = c.choreName
            chore.assignedBy = c.assignedBy
            chore.assignedTo = c.assignedTo

            choreListItems!!.add(chore)

        }

        adapter!!.notifyDataSetChanged()

    }
}