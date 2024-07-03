package it2161.assignment2.popularmovies

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class Movie(
    @PrimaryKey val id: Int,
    @ColumnInfo("poster_path") val poster_path: String?,
    @ColumnInfo("adult") val adult: Boolean?,
    @ColumnInfo("overview") val overview: String?,
    @ColumnInfo("release_date") val release_date: String?,
    @ColumnInfo("genre_ids") val genre_ids: String?,
    @ColumnInfo("original_title") val original_title: String?,
    @ColumnInfo("original_language") val original_language: String?,
    @ColumnInfo("title") val title: String?,
    @ColumnInfo("backdrop_path") val backdrop_path: String?,
    @ColumnInfo("popularity") val popularity: Double,
    @ColumnInfo("vote_count") val vote_count: Int,
    @ColumnInfo("video") val video: Boolean?,
    @ColumnInfo("vote_average") val vote_average: Double
)