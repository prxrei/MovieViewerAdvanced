package it2161.assignment2.popularmovies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_list_of_movies.movielist
import kotlinx.android.synthetic.main.activity_view_list_of_movies.noitemslist
import kotlinx.android.synthetic.main.list_view_movie.view.moviePoster
import kotlinx.android.synthetic.main.list_view_movie.view.movieTitle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewListOfMoviesActivity : AppCompatActivity() {

    var allMovies : List<Movie>? = null

    val SHOW_BY_TOP_RATED = 1
    val SHOW_BY_POPULAR = 2

    private var displayType = SHOW_BY_TOP_RATED

    private var scrollPosition: Int = 0

    private val moviesViewModel: MovieViewModel by viewModels(){
        MoviesViewModelFactory((application as MyMovies).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list_of_movies)

        moviesViewModel.allMovies.observe(this) {
            allMovies = it
            lifecycleScope.launch {
                it?.let {
                    val moviesAdapter = object : BaseAdapter() {
                        override fun getCount(): Int {
                            return it.size
                        }

                        override fun getItem(position: Int): Any {
                            return it[position]
                        }

                        override fun getItemId(position: Int): Long {
                            return position.toLong()
                        }

                        override fun getView(
                            position: Int,
                            convertView: View?,
                            parent: ViewGroup?
                        ): View {
                            val v: View =
                                layoutInflater.inflate(R.layout.list_view_movie, parent, false)

                            val titleTextView: TextView = v.movieTitle
                            val posterImageView: ImageView = v.moviePoster

                            val currentMovieItem: Movie = getItem(position) as Movie

                            titleTextView.text = currentMovieItem.title // Title
                            val moviepath = NetworkUtils.buildImageUrl(currentMovieItem.poster_path)?.toString()
                            Log.w("SHOW", "${currentMovieItem.poster_path}")
                            Picasso.with(this@ViewListOfMoviesActivity).load(moviepath).into(posterImageView)
                            return v
                        }
                    }
                    movielist.adapter = moviesAdapter
                    scrollPosition = movielist.firstVisiblePosition
                    movielist.setOnItemClickListener { _, _, position, _ ->
                        val selectedItem: Movie = movielist.getItemAtPosition(position) as Movie
                        val myIntent =
                            Intent(this@ViewListOfMoviesActivity, ItemDetailActivity::class.java)
                        myIntent.putExtra("poster_path", selectedItem.poster_path)
                        myIntent.putExtra("title", selectedItem.title)
                        myIntent.putExtra("overview", selectedItem.overview)
                        myIntent.putExtra("release_date", selectedItem.release_date)
                        myIntent.putExtra("popularity", selectedItem.popularity)
                        myIntent.putExtra("vote_count", selectedItem.vote_count)
                        myIntent.putExtra("vote_average", selectedItem.vote_average)
                        myIntent.putExtra("language", selectedItem.original_language)
                        myIntent.putExtra("is_adult", selectedItem.adult)
                        myIntent.putExtra("has_video", selectedItem.video)
                        startActivity(myIntent)
                    }
                }
                togglevisibility()
            }
        }
    }

    private fun togglevisibility(){
        if (movielist.count > 0){
            noitemslist.visibility = View.GONE
            movielist.visibility = View.VISIBLE
        } else {
            noitemslist.visibility = View.VISIBLE
            movielist.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        loadMovieData(displayType)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("scrollPosition", scrollPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        scrollPosition = savedInstanceState.getInt("scrollPosition", 0)
        movielist.setSelection(scrollPosition)
    }

    fun loadMovieData(viewType: Int) {

        var showTypeStr: String? = null
        when (viewType) {
            SHOW_BY_TOP_RATED -> showTypeStr = NetworkUtils.TOP_RATED_PARAM
            SHOW_BY_POPULAR -> showTypeStr = NetworkUtils.POPULAR_PARAM

        }

        if (showTypeStr != null) {
            displayType = viewType
            lifecycleScope.launch(Dispatchers.IO) {
                val apiKey = getString(R.string.moviedb_api_key)
                val queryType = showTypeStr
                val response = NetworkUtils.getResponseFromHttpUrl(
                    NetworkUtils.buildUrl(queryType, apiKey) ?: return@launch
                )

                response?.let {
                    withContext(Dispatchers.Main) {
                        val movielist = movieDBJsonUtils.getMovieDetailsFromJson(this@ViewListOfMoviesActivity, it)
                        movielist?.let {
                            moviesViewModel.removeAllMovies()
                            it.forEach { movieItem ->
                                moviesViewModel.insert(
                                    Movie(
                                        movieItem.id,
                                        movieItem.poster_path,
                                        movieItem.adult,
                                        movieItem.overview,
                                        movieItem.release_date,
                                        movieItem.genre_ids,
                                        movieItem.original_title,
                                        movieItem.original_langauge,
                                        movieItem.title,
                                        movieItem.backdrop_path,
                                        movieItem.popularity,
                                        movieItem.vote_count,
                                        movieItem.video,
                                        movieItem.vote_average
                                    )
                                )
                            }
                        }
                        togglevisibility()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.sortPopular -> {
                loadMovieData(SHOW_BY_POPULAR)
            }
            R.id.sortTopRated -> {
                loadMovieData(SHOW_BY_TOP_RATED)
            }

        }

        return super.onOptionsItemSelected(item)
    }
}
