package com.example.moviepedia.tmdb

import com.example.moviepedia.model.GetTVSeasonsResponse
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.model.TVShowTMDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    private val api: Api

    // TODO add region parameter

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/") .addConverterFactory(GsonConverterFactory.create()) .build()
        api = retrofit.create(Api::class.java) }

    fun getTVSeasons(
            tv_id: Int,
            season_number: Int,
            onSuccess: (response: GetTVSeasonsResponse) -> Unit,
            onError: () -> Unit
    ) {
        api.getTVSeasons(tv_id = tv_id, season_number = season_number)
            .enqueue(object : Callback<GetTVSeasonsResponse> {
                override fun onResponse(call: Call<GetTVSeasonsResponse>, response: Response<GetTVSeasonsResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetTVSeasonsResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getTVShowDetail(
        tvshow: TVShowTMDB,
        onSuccess: (response: GetTVShowDetailResponse) -> Unit,
        onError: () -> Unit
    ) {
        api.getTVShowDetail(tv_id = tvshow.id.toInt())
            .enqueue(object : Callback<GetTVShowDetailResponse> {
                override fun onResponse(call: Call<GetTVShowDetailResponse>, response: Response<GetTVShowDetailResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetTVShowDetailResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getMovieDetail(
        movie: MovieTMDB,
        onSuccess: (response: GetMovieDetailResponse) -> Unit,
        onError: () -> Unit
    ) {
        api.getMovieDetail(movie_id = movie.id.toInt())
            .enqueue(object  : Callback<GetMovieDetailResponse> {
                override fun onResponse(
                    call: Call<GetMovieDetailResponse>,
                    response: Response<GetMovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMovieDetailResponse>, t: Throwable) {
                    onError.invoke()
                }

            })
    }

    fun getMovieCredits(
        movie: MovieTMDB,
        onSuccess: (response: GetMovieCreditsResponse) -> Unit,
        onError: () -> Unit
    ) {
        api.getMovieCredits(movie_id = movie.id.toInt())
            .enqueue(object : Callback<GetMovieCreditsResponse> {
                override fun onResponse(
                    call: Call<GetMovieCreditsResponse>,
                    response: Response<GetMovieCreditsResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMovieCreditsResponse>, t: Throwable) {
                    onError.invoke()
                }

            })
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<MovieTMDB>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getMovieProviders(
        movie: MovieTMDB,
        onSuccess: (response: GetMovieProvidersResponse) -> Unit,
        onError: () -> Unit
    ) {
        api.getMovieProviders(movie_id = movie.id.toInt())
                .enqueue(object : Callback<GetMovieProvidersResponse> {
                    override fun onFailure(call: Call<GetMovieProvidersResponse>, t: Throwable) {
                        onError.invoke()
                    }

                    override fun onResponse(
                            call: Call<GetMovieProvidersResponse>,
                            response: Response<GetMovieProvidersResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                })
    }


    fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: List<MovieTMDB>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopRatedMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getRecommendedMovies(
            movie: MovieTMDB,
            page: Int = 1,
            onSuccess: (movies: List<MovieTMDB>) -> Unit,
            onError: () -> Unit
    ) {
        api.getRecommendedMovies(movie_id = movie.id.toInt(), page = page)
                .enqueue(object : Callback<GetMoviesResponse> {
                    override fun onResponse(
                            call: Call<GetMoviesResponse>,
                            response: Response<GetMoviesResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movies)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
    }

    fun getSimilariMovies(
            movie: MovieTMDB,
            page: Int = 1,
            onSuccess: (movies: List<MovieTMDB>) -> Unit,
            onError: () -> Unit
    ) {
        api.getSimilarMovies(movie_id = movie.id.toInt(), page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                        call: Call<GetMoviesResponse>,
                        response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: List<MovieTMDB>) -> Unit,
        onError: () -> Unit
    ) {
        api.getUpcomingMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getLatestMovies(
        onSuccess: (movies: List<MovieTMDB>) -> Unit,
        onError: () -> Unit
    ) {
        api.getLatestMovies()
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun searchMovie(
            page: Int = 1,
            language: String = "en-US",
            query: String,
            onSuccess: (movies: List<MovieTMDB>) -> Unit,
            onError: () -> Unit
    ) {
        api.searchMovie(language = language, query = query, page = page)
                .enqueue(object : Callback<GetMoviesResponse> {
                    override fun onResponse(
                            call: Call<GetMoviesResponse>,
                            response: Response<GetMoviesResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movies)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
    }

    fun searchTVShow(
            page: Int = 1,
            language: String = "en-US",
            query: String,
            onSuccess: (movies: List<TVShowTMDB>) -> Unit,
            onError: () -> Unit
    ) {
        api.searchTVShow(language = language, query = query, page = page)
                .enqueue(object : Callback<GetTVShowResponse> {
                    override fun onResponse(
                            call: Call<GetTVShowResponse>,
                            response: Response<GetTVShowResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.shows)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<GetTVShowResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
    }

    /**
     * TV shows functions
     */

    fun getPopularTVShow(
        page: Int = 1,
        onSuccess: (shows: List<TVShowTMDB>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularTVShow(page = page)
            .enqueue(object : Callback<GetTVShowResponse> {
                override fun onResponse(
                    call: Call<GetTVShowResponse>,
                    response: Response<GetTVShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.shows)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetTVShowResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getTopRatedTVShow(
        page: Int = 1,
        onSuccess: (shows: List<TVShowTMDB>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopRatedTVShow(page = page)
            .enqueue(object : Callback<GetTVShowResponse> {
                override fun onResponse(
                    call: Call<GetTVShowResponse>,
                    response: Response<GetTVShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.shows)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetTVShowResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getLatestTVShow(
        onSuccess: (shows: List<TVShowTMDB>) -> Unit,
        onError: () -> Unit
    ) {
        api.getLatestTVShow()
            .enqueue(object : Callback<GetTVShowResponse> {
                override fun onResponse(
                    call: Call<GetTVShowResponse>,
                    response: Response<GetTVShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.shows)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetTVShowResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}
