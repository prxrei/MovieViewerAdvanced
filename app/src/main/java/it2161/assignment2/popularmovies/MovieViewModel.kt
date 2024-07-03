package it2161.assignment2.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MovieViewModel (val repo: MoviesRepository): ViewModel(){

    val allMovies: LiveData<List<Movie>> = repo.allMovies.asLiveData()

    fun insert(movieitem: Movie) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(movieitem)
    }

    fun removeAllMovies() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAllMovies()
    }
}

class MoviesViewModelFactory(private val repo: MoviesRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}