package com.example.apimoviedbappsbagus

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apimoviedbappsbagus.Adapter.MovieAdapter
import com.example.apimoviedbappsbagus.Model.ResponseListMovies
import com.example.apimoviedbappsbagus.Model.Results
import com.example.apimoviedbappsbagus.Service.RetrofitInstance
import com.example.apimoviedbappsbagus.databinding.ActivityMovieByGenreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class MovieByGenreActivity : AppCompatActivity() {
    lateinit var binding  : ActivityMovieByGenreBinding
    val listMovies = arrayListOf<Results>()
    var page = 1
    var loading = true
    var pastItemsVisible: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    lateinit var lm  : LinearLayoutManager
    var genreId = ""

    private lateinit var movieAdapter : MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieByGenreBinding.inflate(layoutInflater)
        genreId = intent.getStringExtra("genreId",).toString()

        Log.d("inigenreid", "onCreate: " + genreId)
        setContentView(binding.root)
        getMoviesGenre(genreId,page)
        prepareRecyclerView()

    }
    private fun prepareRecyclerView() {
        lm = LinearLayoutManager(this)

        movieAdapter = MovieAdapter(this,listMovies)

        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerView.layoutManager = lm
        movieAdapter = MovieAdapter(this,listMovies)
        binding.mRecyclerView.adapter = movieAdapter


        binding.mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = lm.getChildCount()
                    totalItemCount = lm.getItemCount()
                    pastItemsVisible = lm.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastItemsVisible >= totalItemCount) {
                            loading = false
                            Log.v("...", "Last Item !")
                            loadmore()
                            // Do pagination.. i.e. fetch new data
                            loading = true
                        }
                    }
                }
            }
        })


    }
    fun loadmore()
    {
        page+=1
        getMoviesGenre(genreId,page)
    }
    fun getMoviesGenre(genreId:String,page : Int) {
        val params: MutableMap<String, String> = HashMap()
        params["api_key"] = "7916ace8a965a1c3413cd5231af30364"
        params["language"] = "en-US"
        params["with_genres"] = genreId
        params["page"] = page.toString()
        RetrofitInstance.api.getDiscovery(params).enqueue(object  :
            Callback<ResponseListMovies> {
            override fun onResponse(call: Call<ResponseListMovies>, response: Response<ResponseListMovies>) {
                if (response.body()!=null){
                    listMovies.addAll(response.body()!!.results)
                    Log.d(ContentValues.TAG, "inionResponse: "  + listMovies)
                    movieAdapter.notifyDataSetChanged()
                }
                else{
                    Log.d("TAGihan", response.code().toString())

                }
            }
            override fun onFailure(call: Call<ResponseListMovies>, t: Throwable) {

                Log.d("TAGihan", t.message.toString())
            }
        })
    }
}