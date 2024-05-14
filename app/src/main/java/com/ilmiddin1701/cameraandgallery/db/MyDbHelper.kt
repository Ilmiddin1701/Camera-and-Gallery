package com.ilmiddin1701.cameraandgallery.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ilmiddin1701.cameraandgallery.models.ImageData

class MyDbHelper(context: Context): SQLiteOpenHelper(context, "db_name", null, 1), MyDbInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        val imageQuery = "create table image_table (id integer not null primary key autoincrement unique, name text not null, image text not null)"
        db?.execSQL(imageQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addImage(imageData: ImageData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", imageData.name)
        contentValues.put("image", imageData.image)
        database.insert("image_table", null, contentValues)
        database.close()
    }

    override fun showImages(): List<ImageData> {
        val list = ArrayList<ImageData>()
        val database = this.readableDatabase
        val query = "select * from image_table"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                list.add(
                    ImageData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }
}