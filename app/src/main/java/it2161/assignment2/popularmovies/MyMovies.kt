package it2161.assignment2.popularmovies

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyMovies(): Application () {
    val appScope = CoroutineScope(SupervisorJob())
    val db by lazy { MoviesRoomDatabase.getDatabase(this, appScope) }
    val repo by lazy { MoviesRepository(db!!.MovieDAO()) }
}