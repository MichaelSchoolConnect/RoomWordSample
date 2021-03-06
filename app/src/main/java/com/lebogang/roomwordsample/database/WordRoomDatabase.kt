package com.lebogang.roomwordsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lebogang.roomwordsample.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()

                    //Delete all content here.
                    wordDao.deleteAll()

                    //Add sample words.
                    var word = Word("Hello")
                    wordDao.insert(word)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

/**
* Let's walk through the code:

    The database class for Room must be abstract and extend RoomDatabase
    You annotate the class to be a Room database with @Database and use the annotation parameters to declare the entities that belong in the database and set the version number. Each entity corresponds to a table that will be created in the database. Database migrations are beyond the scope of this codelab, so we set exportSchema to false here to avoid a build warning. In a real app, you should consider setting a directory for Room to use to export the schema so you can check the current schema into your version control system.
    The database exposes DAOs through an abstract "getter" method for each @Dao.
    We've defined a singleton, WordRoomDatabase, to prevent having multiple instances of the database opened at the same time.
    getDatabase returns the singleton. It'll create the database the first time it's accessed, using Room's database builder to create a RoomDatabase object in the application context from the WordRoomDatabase class and names it "word_database".

* */