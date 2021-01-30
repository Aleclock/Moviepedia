package com.example.moviepedia.model

import com.google.firebase.Timestamp

class FirestoreEpisode (
  val id_tvshow: Long = -1,
  val id_season: Int = -1,
  val id_episode: Int = -1,
  val name_tvshow: String = "",
  val name_episode: String = "",
  val genre_ids_tvshow: List<Int> = listOf(),
  val origin_country: List<String> = listOf(),
  val tvshow_popularity : Float = 0f,
  val original_language : String = "",
  val tvshow_poster_path : String = "",
  val tvshow_backdrop_path : String = "",
  val episode_number : Int = -1,
  val episode_air_date : String = "",
  val episode_overview : String = "",
  val episode_vote_average : Float = 0f,
  val episode_vote_count : Int = -1,
  val season_air_date : String = "",
  val season_episode_count : Int = -1,
  val season_overview  : String = "",
  val season_poster_path : String = "",
  val season_number : Int = -1,
  var addDate: Timestamp? = null
){
  fun episodeToFirestoreEpisode(tvShowTMDB: TVShowTMDB, seasonsTMDB: SeasonsTMDB, episodeTMDB: EpisodeTMDB) : FirestoreEpisode {
    return FirestoreEpisode(
      tvShowTMDB.id,
      seasonsTMDB.id,
      episodeTMDB.id,
      tvShowTMDB.name,
      episodeTMDB.name,
      tvShowTMDB.genre_ids,
      tvShowTMDB.origin_country,
      tvShowTMDB.popularity,
      tvShowTMDB.original_language,
      tvShowTMDB.posterPath,
      tvShowTMDB.posterPath,
      episodeTMDB.episode_number,
      episodeTMDB.air_date,
      episodeTMDB.overview,
      episodeTMDB.vote_average,
      episodeTMDB.vote_count,
      seasonsTMDB.air_date,
      seasonsTMDB.episode_count,
      seasonsTMDB.overview,
      seasonsTMDB.poster_path,
      seasonsTMDB.season_number,
      null
    )
  }
}