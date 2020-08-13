package com.example.choresapplocaldbexample.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.choresapplocaldbexample.R
import com.example.choresapplocaldbexample.model.Chore

class ChoreListAdapter(private val list: ArrayList<Chore>,
                        private val context: Context) : RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindViews(list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var chorename = itemView.findViewById(R.id.list_chore_name) as TextView
        var assignedBy = itemView.findViewById(R.id.list_assigned_by) as TextView
        var assignedDate = itemView.findViewById(R.id.list_date) as TextView
        var assignedTo = itemView.findViewById(R.id.list_assigned_to) as TextView

        fun bindViews(chore: Chore)
        {
            chorename.text = chore.choreName
            assignedBy.text = chore.assignedBy
            //assignedDate.text = chore.showHumanDate(chore.timeAssigned!!)
            assignedTo.text = chore.assignedTo


        }
    }
}