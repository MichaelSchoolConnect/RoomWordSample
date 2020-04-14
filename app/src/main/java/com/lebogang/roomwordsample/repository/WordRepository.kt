package com.lebogang.roomwordsample.repository

import androidx.lifecycle.LiveData
import com.lebogang.roomwordsample.database.WordDao
import com.lebogang.roomwordsample.entity.Word

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}

/**
* The main takeaways:

    The DAO is passed into the repository constructor as opposed to the whole database.
    This is because it only needs access to the DAO, since the DAO contains all the read/write
    methods for the database. There's no need to expose the entire database to the repository.
    The list of words is a public property. It's initialized by getting the LiveData list of words
    from Room; we can do this because of how we defined the getAlphabetizedWords method to return
    LiveData in the "The LiveData class" step. Room executes all queries on a separate thread.
    Then observed LiveData will notify the observer on the main thread when the data has changed.
    The suspend modifier tells the compiler that this needs to be called from a coroutine or another
    suspending function.

* */