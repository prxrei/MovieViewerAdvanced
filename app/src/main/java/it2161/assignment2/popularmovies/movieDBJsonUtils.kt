package it2161.assignment2.popularmovies

import android.content.Context
import it2161.assignment2.popularmovies.entity.MovieItem
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class movieDBJsonUtils() {

    companion object {

        @Throws(JSONException::class)
        fun getMovieDetailsFromJson(context: Context, movieDetailsJsonStr: String): ArrayList<MovieItem>? {

            val parsedMovieData = ArrayList<MovieItem>()
            val jsondetails = JSONObject(movieDetailsJsonStr)
            val moviesArray = jsondetails.getJSONArray("results")

            for (i in 0 until moviesArray.length()) {
                val movies = moviesArray.getJSONObject(i)

                val Movie = MovieItem(
                    movies.getString("poster_path"),
                    movies.getBoolean("adult"),
                    movies.getString("overview"),
                    movies.getString("release_date"),
                    movies.getString("genre_ids"),
                    movies.getInt("id"),
                    movies.getString("original_title"),
                    movies.getString("original_language"),
                    movies.getString("title"),
                    movies.getString("backdrop_path"),
                    movies.getDouble("popularity"),
                    movies.getInt("vote_count"),
                    movies.getBoolean("video"),
                    movies.getDouble("vote_average"))

                parsedMovieData.add(Movie)
            }
            return parsedMovieData
        }
    }
}
