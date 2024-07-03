package it2161.assignment2.popularmovies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("Select * from movies_table")
    fun retrieveMovies(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newMovie: Movie)

    @Delete
    fun delete(delMovie: Movie)

    @Query("DELETE FROM movies_table")
    fun deleteAllMovies()
}