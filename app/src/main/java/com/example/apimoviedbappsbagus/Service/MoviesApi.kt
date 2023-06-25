package com.example.apimoviedbappsbagus.Service

import com.example.apimoviedbappsbagus.Model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MoviesApi {
    @GET("movie/now_playing")
    fun getNowPlaying( @QueryMap params : Map<String,String>) : Call<ResponseListMovies>
    @GET("movie/popular?")
    fun getPopularMovies( @QueryMap params : Map<String,String>) : Call<ResponseListMovies>
    @GET("movie/{movie_id}?")
    fun getDetailMovies(@Path("movie_id") idmovie:String, @QueryMap params : Map<String,String> ) : Call<DetailMovieModel>

    @GET("movie/{movie_id}/videos")
    fun getDetailVideosMovies(@Path("movie_id") idmovie:String, @QueryMap params : Map<String,String> ) : Call<ResponseVideosTrailer>
    @GET("search/movie?")
    fun getSearchMovie( @QueryMap params : Map<String,String>) : Call<ResponseListMovies>
    @GET("discover/movie")
    fun getDiscovery( @QueryMap params : Map<String,String>) : Call<ResponseListMovies>
    @GET("genre/movie/list")
    fun getGenre( @QueryMap params : Map<String,String>) : Call<ResponseGenres>
    @GET("movie/{movie_id}/reviews")
    fun getMoviesReviews(@Path("movie_id") idmovie:String, @QueryMap params : Map<String,String> ) : Call<ResponseReviewMovies>

}