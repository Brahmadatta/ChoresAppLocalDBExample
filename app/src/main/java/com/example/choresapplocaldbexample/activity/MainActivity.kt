package com.example.choresapplocaldbexample.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.example.choresapplocaldbexample.R
import com.example.choresapplocaldbexample.data.ChoresDatabaseHandler
import com.example.choresapplocaldbexample.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler : ChoresDatabaseHandler ? = null

    var progressBar : ProgressDialog ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)

        progressBar = ProgressDialog(this)


        save.setOnClickListener {

            progressBar!!.setTitle("Saving..")
            if (!TextUtils.isEmpty(choreName.text.toString()) && !TextUtils.isEmpty(assignedById.text.toString())
                && !TextUtils.isEmpty(assigntold.text.toString()))
            {
                //save to Database
                var chore = Chore()
                chore.choreName = choreName.text.toString()
                chore.assignedBy = assignedById.text.toString()
                chore.assignedTo = assigntold.text.toString()

                saveChoreToDb(chore)
                progressBar!!.cancel()
                startActivity(Intent(this,ChoreListActivity::class.java))

            }else{
                Toast.makeText(this,"Please enter a chore",Toast.LENGTH_LONG).show()
            }
        }

//        var chore = Chore()
//
//        chore.choreName = "Clean Room"
//        chore.assignedBy = "Datta"
//        chore.assignedTo = "Mahesh"
//
//        dbHandler!!.createChore(chore)
//
//
//        //read from database
//        var chores : Chore = dbHandler!!.readChore(1)
//        Log.d("readChore",chores.assignedBy)

    }


    fun saveChoreToDb(chore: Chore)
    {
        dbHandler!!.createChore(chore)
    }
}