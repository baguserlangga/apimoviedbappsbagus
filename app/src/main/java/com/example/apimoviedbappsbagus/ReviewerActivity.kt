package com.example.apimoviedbappsbagus

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apimoviedbappsbagus.Adapter.MovieAdapter
import com.example.apimoviedbappsbagus.Adapter.ReviewerAdapter
import com.example.apimoviedbappsbagus.Model.ResponseReviewMovies
import com.example.apimoviedbappsbagus.Model.ReviewModel
import com.example.apimoviedbappsbagus.Service.RetrofitInstance
import com.example.apimoviedbappsbagus.databinding.ActivityReviewerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ReviewerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityReviewerBinding
    private lateinit var reviewerAdapter : ReviewerAdapter
    val listMovies = arrayListOf<ReviewModel>()
    var idmovie :String=""
    //    val  lm = LinearLayoutManager(this)
    var page = 1

    var loading = true
    var pastItemsVisible: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    lateinit var lm  : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewerBinding.inflate(layoutInflater)

        idmovie = intent.getStringExtra("movieId").toString()

        prepareRecyclerView()

        getPopularMovies(idmovie,page)
        setContentView(binding.root)
    }

    private fun prepareRecyclerView() {
        lm = LinearLayoutManager(this)

        reviewerAdapter = ReviewerAdapter(this,listMovies)

        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerView.layoutManager = lm
        reviewerAdapter = ReviewerAdapter(this,listMovies)
        binding.mRecyclerView.adapter = reviewerAdapter


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
        getPopularMovies(idmovie,page)
    }
    fun clear()
    {
        listMovies.clear()

    }

    fun getPopularMovies(movieid :String, page:Int) {
        val params: MutableMap<String, String> = HashMap()
        params["api_key"] = "7916ace8a965a1c3413cd5231af30364"
        params["language"] = "en-US"
        params["page"] = page.toString()

        RetrofitInstance.api.getMoviesReviews(movieid, params).enqueue(object  :
            Callback<ResponseReviewMovies> {
            override fun onResponse(call: Call<ResponseReviewMovies>, response: Response<ResponseReviewMovies>) {
                if (response.body()!=null){
                    listMovies.addAll(response.body()!!.results)
                    Log.d(ContentValues.TAG, "inionResponse: "  + listMovies)
                    reviewerAdapter.notifyDataSetChanged()
                }
                else{
                    Log.d("TAGihan", response.code().toString())

                }
            }
            override fun onFailure(call: Call<ResponseReviewMovies>, t: Throwable) {

                Log.d("TAGihan", t.message.toString())
            }
        })
    }


}