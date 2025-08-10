package com.example.noteton.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = true)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private const val DB_NAME = "noteton.db"


        fun getInstance(context: Context): NotesDatabase {
            return Room.databaseBuilder(
                context,
                NotesDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}