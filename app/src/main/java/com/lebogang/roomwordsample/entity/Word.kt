package com.lebogang.roomwordsample.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* This class will describe the Entity (which represents the SQLite table) for your words.
* Each property in the class represents a column in the table. Room will ultimately use these
* properties to both create the table and instantiate objects from rows in the database.
* */
@Entity(tableName = "word_table")
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)

/**
*Let's see what these annotations do:

    @Entity(tableName = "word_table")
    Each @Entity class represents a SQLite table. Annotate your class declaration to indicate that
    * it's an entity. You can specify the name of the table if you want it to be different from the
    * name of the class. This names the table "word_table".
    *
    @PrimaryKey
    Every entity needs a primary key. To keep things simple, each word acts as its own primary key.
    *
    @ColumnInfo(name = "word")
    Specifies the name of the column in the table if you want it to be different from the name of
    * the member variable. This names the column "word".
    Every property that's stored in the database needs to have public visibility, which is the
    * Kotlin default.

*  */