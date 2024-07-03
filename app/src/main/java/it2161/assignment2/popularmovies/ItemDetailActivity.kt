package it2161.assignment2.popularmovies

import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_detail.movie_hasvideo
import kotlinx.android.synthetic.main.activity_item_detail.movie_is_adult
import kotlinx.android.synthetic.main.activity_item_detail.movie_langauge
import kotlinx.android.synthetic.main.activity_item_detail.movie_overview
import kotlinx.android.synthetic.main.activity_item_detail.movie_popularity
import kotlinx.android.synthetic.main.activity_item_detail.movie_release_date
import kotlinx.android.synthetic.main.activity_item_detail.movie_vote_avg
import kotlinx.android.synthetic.main.activity_item_detail.movie_vote_count

class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val actionbar = supportActionBar

        actionbar?.setDisplayHomeAsUpEnabled(true)

        val moviePosterPath = intent.getStringExtra("poster_path")
        val movieTitle = intent.getStringExtra("title")
        val movieOverview = intent.getStringExtra("overview")
        val movieReleaseDate = intent.getStringExtra("release_date")
        val moviePopularity = intent.getDoubleExtra("popularity", 0.0)
        val movieVoteCount = intent.getIntExtra("vote_count", 0)
        val movieVoteAverage = intent.getDoubleExtra("vote_average", 0.0)
        val movieLanguage = intent.getStringExtra("language")
        val movieIsAdult = intent.getBooleanExtra("is_adult", false)
        val movieHasVideo = intent.getBooleanExtra("has_video", false)

        val moviepath = NetworkUtils.buildImageUrl(moviePosterPath).toString()
        val posterImageView: ImageView = findViewById(R.id.posterIV)
        Picasso.with(this@ItemDetailActivity).load(moviepath).into(posterImageView)

        title = movieTitle
        movie_overview.text = movieOverview
        movie_release_date.text = movieReleaseDate
        movie_popularity.text = moviePopularity.toString()
        movie_vote_count.text = movieVoteCount.toString()
        movie_vote_avg.text = movieVoteAverage.toString()
        movie_langauge.text = movieLanguage
        movie_is_adult.text = movieIsAdult.toString()
        movie_hasvideo.text = movieHasVideo.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed(){
        super.onBackPressed()
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(R.layout.activity_item_details_horizontal)
    }
}
