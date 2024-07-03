package it2161.assignment2.popularmovies

class MoviesRepository(private val movieDAO: MovieDAO) {

    val allMovies = movieDAO.retrieveMovies()

    suspend fun insert(movie: Movie){
        movieDAO.insert(movie)
    }

    suspend fun delete(movie: Movie){
        movieDAO.delete(movie)
    }

    suspend fun deleteAllMovies() {
        movieDAO.deleteAllMovies()
    }
}