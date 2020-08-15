package com.example.choresapplocaldbexample.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.choresapplocaldbexample.R
import com.example.choresapplocaldbexample.data.ChoreListAdapter
import com.example.choresapplocaldbexample.data.ChoresDatabaseHandler
import com.example.choresapplocaldbexample.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup.view.*
import java.util.ArrayList

class ChoreListActivity : AppCompatActivity() {

    private var adapter : ChoreListAdapter? = null
    private var chorelist : ArrayList<Chore> ?= null
    private var layoutManager : RecyclerView.LayoutManager ?= null
    var databaseHandler : ChoresDatabaseHandler ?= null
    var choreListItems : ArrayList<Chore> ?= null

    private var dialogBuilder : AlertDialog.Builder?= null
    private var dialog : AlertDialog?= null

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
        chorelist!!.reverse() //To show the latest chore on top

        for (c in chorelist!!.iterator())
        {
             val chore = Chore()
            chore.id = c.id
            chore.choreName = "Chore : ${c.choreName}"
            chore.assignedBy = "Assigned By : ${c.assignedBy}"
            chore.assignedTo = "Assigned To : ${c.assignedTo}"
            chore.showHumanDate(c.timeAssigned!!)

            choreListItems!!.add(chore)

        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_menu_button){
            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }


    fun createPopupDialog(){
        var view = layoutInflater.inflate(R.layout.popup,null)
        var choreName = view.popup_enter_chore
        var choreAssignedBy = view.popup_enter_assignedBy
        var choreAssignedTo = view.popup_enter_assignto
        var savechore = view.popup_save_chore

        savechore.setOnClickListener {

            if (!TextUtils.isEmpty(choreName.text.toString().trim()) &&
                !TextUtils.isEmpty(choreAssignedBy.text.toString().trim()) &&
                !TextUtils.isEmpty(choreAssignedTo.text.toString().trim()))
            {

                var chore = Chore()
                chore.choreName = choreName.text.toString().trim()
                chore.assignedBy = choreAssignedBy.text.toString().trim()
                chore.assignedTo = choreAssignedTo.text.toString().trim()
                databaseHandler!!.createChore(chore)

                dialog!!.dismiss()
                startActivity(Intent(this,ChoreListActivity::class.java))
                finish()

            }else{

            }
        }



        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()
    }
}