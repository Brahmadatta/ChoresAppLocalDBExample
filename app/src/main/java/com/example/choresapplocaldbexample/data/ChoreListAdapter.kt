package com.example.choresapplocaldbexample.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.choresapplocaldbexample.R
import com.example.choresapplocaldbexample.activity.ChoreListActivity
import com.example.choresapplocaldbexample.model.Chore
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListAdapter(private val list: ArrayList<Chore>,
                        private val context: Context) : RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row,parent,false)
        return ViewHolder(view,context,list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindViews(list[position])
    }

    inner class ViewHolder(itemView: View,context: Context,list: ArrayList<Chore>) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var mlist = list

        var mContext = context
        var chorename = itemView.findViewById(R.id.list_chore_name) as TextView
        var assignedBy = itemView.findViewById(R.id.list_assigned_by) as TextView
        var assignedDate = itemView.findViewById(R.id.list_date) as TextView
        var assignedTo = itemView.findViewById(R.id.list_assigned_to) as TextView
        var deleteButton = itemView.findViewById(R.id.list_delete_button) as Button
        var editButton = itemView.findViewById(R.id.list_edit_button) as Button



        fun bindViews(chore: Chore)
        {
            chorename.text = chore.choreName
            assignedBy.text = chore.assignedBy
            assignedDate.text = chore.showHumanDate(System.currentTimeMillis())
            assignedTo.text = chore.assignedTo


            deleteButton.setOnClickListener(this)
            editButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            var mPosition : Int = adapterPosition
            var chore = mlist[mPosition]

            when(v?.id)
            {
                deleteButton.id -> {
                    deleteChore(chore.id!!.toInt())
                    mlist.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                editButton.id -> {
                    editChore(chore)
                }
            }
        }

        fun deleteChore(id : Int){
            var db : ChoresDatabaseHandler = ChoresDatabaseHandler(mContext)
            db.deleteChore(id)
        }

        fun editChore(chore: Chore)
        {
            var dialogBuilder : AlertDialog.Builder
            var dialog : AlertDialog ?= null
            var databaseHandler : ChoresDatabaseHandler = ChoresDatabaseHandler(context)
            var view = LayoutInflater.from(mContext).inflate(R.layout.popup,null)
            var choreName = view.popup_enter_chore
            var choreAssignedBy = view.popup_enter_assignedBy
            var choreAssignedTo = view.popup_enter_assignto
            var savechore = view.popup_save_chore

            savechore.setOnClickListener {

                if (!TextUtils.isEmpty(choreName.text.toString().trim()) &&
                    !TextUtils.isEmpty(choreAssignedBy.text.toString().trim()) &&
                    !TextUtils.isEmpty(choreAssignedTo.text.toString().trim()))
                {

                    //var chore = Chore()

                    chore.choreName = choreName.text.toString().trim()
                    chore.assignedBy = choreAssignedBy.text.toString().trim()
                    chore.assignedTo = choreAssignedTo.text.toString().trim()
                    databaseHandler!!.updateChore(chore)
                    notifyItemChanged(adapterPosition,chore)

                    dialog!!.dismiss()


                }else{

                }
            }



            dialogBuilder = AlertDialog.Builder(mContext).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()
        }
    }
}