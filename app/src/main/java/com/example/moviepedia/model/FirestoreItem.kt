package com.example.moviepedia.model

class FirestoreItem (
  val id: Long = -1,
  val title: String = "",
  val original_title: String = "",
  val posterPath: String = "",
  val backdropPath: String = "",
  val overview: String = "",
  val genre_ids: List<Int> = listOf(),
  val rating: Float = 0f,
  val popularity: Float = 0f,
  val original_language: String = "",
  val release_date : String? = "",
  val first_air_date : String? = "",
  val vote_count : Int = 0,
  val adult: Boolean? = false,
  val origin_country: List<String>? = listOf()
) {
  fun movieToFirestoreItem(movie: MovieTMDB) : FirestoreItem {
    return FirestoreItem(
      movie.id,
      movie.title,
      movie.original_title,
      movie.posterPath,
      movie.backdropPath,
      movie.overview,
      movie.genre_ids,
      movie.rating,
      movie.popularity,
      movie.original_language,
      movie.releaseDate,
      null,
      movie.vote_count,
      movie.adult,
      null
    )
  }

  fun tvShowToFirestoreItem(tvShow: TVShowTMDB): FirestoreItem? {
    return FirestoreItem(
      tvShow.id,
      tvShow.name,
      tvShow.original_name,
      tvShow.posterPath,
      tvShow.backdropPath,
      tvShow.overview,
      tvShow.genre_ids,
      tvShow.rating,
      tvShow.popularity,
      tvShow.original_language,
      tvShow.first_air_date,
      tvShow.first_air_date,
      tvShow.vote_count,
      null
    )
  }
}