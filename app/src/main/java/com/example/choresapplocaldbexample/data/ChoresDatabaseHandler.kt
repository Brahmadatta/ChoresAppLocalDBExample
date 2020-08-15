package com.example.choresapplocaldbexample.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.choresapplocaldbexample.model.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChoresDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        //SQL - Structured Query Language
        val CREATE_CHORE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                                 KEY_CHORE_NAME + " TEXT," +
                                 KEY_CHORE_ASSIGNED_BY + " TEXT," +
                                 KEY_CHORE_ASSIGNED_TO + " TEXT," +
                                 KEY_CHORE_ASSIGNED_TIME + " LONG" + ")"

        db?.execSQL(CREATE_CHORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        //create table again
        onCreate(db)
    }

    fun createChore(chore: Chore)
    {
        var db : SQLiteDatabase = writableDatabase

        var contentvalues : ContentValues = ContentValues()
        contentvalues.put(KEY_CHORE_NAME,chore.choreName)
        contentvalues.put(KEY_CHORE_ASSIGNED_BY,chore.assignedBy)
        contentvalues.put(KEY_CHORE_ASSIGNED_TO,chore.assignedTo)
        contentvalues.put(KEY_CHORE_ASSIGNED_TIME,System.currentTimeMillis())

        db.insert(TABLE_NAME,null,contentvalues)

        Log.d("DATA Inserted","instered")

        db.close()

    }

    fun readChore(id : Int) : Chore{
        var db : SQLiteDatabase = readableDatabase
        var cursor : Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID,
                                        KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_BY, KEY_CHORE_ASSIGNED_TO,
            KEY_CHORE_ASSIGNED_TIME), KEY_ID + "=?", arrayOf(id.toString()),
            null,null,null,null)

        if (cursor != null)
            cursor.moveToFirst()
            var chore = Chore()
            chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
            chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
            chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
            chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))


            var dateFormat : java.text.DateFormat = DateFormat.getDateInstance()
            var formattedDate = dateFormat.format(Date(cursor
                .getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time)

        return chore
    }

    fun readChores() : ArrayList<Chore> {
        var db : SQLiteDatabase = readableDatabase

        var list : ArrayList<Chore> = ArrayList()

        var selectAll = "SELECT * FROM " + TABLE_NAME

        var cursor : Cursor = db.rawQuery(selectAll,null)

        //loop through our chores
        if (cursor.moveToFirst()) {
            do {
                var chore = Chore()

                chore.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
                chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))

                list.add(chore)

            }while (cursor.moveToNext())
        }


        return list

    }

    fun updateChore(chore: Chore)
    {
        var db : SQLiteDatabase = writableDatabase
        var contentvalues : ContentValues = ContentValues()
        contentvalues.put(KEY_CHORE_NAME,chore.choreName)
        contentvalues.put(KEY_CHORE_ASSIGNED_BY,chore.assignedBy)
        contentvalues.put(KEY_CHORE_ASSIGNED_TO,chore.assignedTo)
        contentvalues.put(KEY_CHORE_ASSIGNED_TIME,System.currentTimeMillis())

        db.update(TABLE_NAME,contentvalues, KEY_ID + "=?", arrayOf(chore.id.toString()))

    }

    fun deleteChore(id: Int)
    {
        var db : SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))

        db.close()
    }

    fun getChoresCount() : Int{
        var db : SQLiteDatabase = readableDatabase
        var count_query = "SELECT * FROM " + TABLE_NAME
        var cursor : Cursor = db.rawQuery(count_query,null)
        return cursor.count
    }
}