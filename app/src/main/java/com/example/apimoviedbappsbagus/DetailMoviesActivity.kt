package com.example.apimoviedbappsbagus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.apimoviedbappsbagus.Adapter.ProductionAdapter
import com.example.apimoviedbappsbagus.Model.DetailMovieModel
import com.example.apimoviedbappsbagus.Model.ResponseVideosTrailer
import com.example.apimoviedbappsbagus.Service.RetrofitInstance
import com.example.apimoviedbappsbagus.databinding.ActivityDetailMoviesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap


class DetailMoviesActivity() : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMoviesBinding
    var movieid :String? =null
    var result : DetailMovieModel?=null
    var viewyoutube = false
    private lateinit var productionAdapter : ProductionAdapter
    lateinit var lm  : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailMoviesBinding.inflate(layoutInflater)
        movieid   = intent.getStringExtra("idMovie")

        val extras = intent.extras
        if (extras != null) {
            val Str = extras.getString("idMovie")
            Log.d("inimovieid", "onCreate: " +movieid)
        }
        binding.buttonViewTrailer.setOnClickListener {
            getTrailerDetail(movieid!!)
        }
        binding.buttonReview.setOnClickListener {
            val intent = Intent(this, ReviewerActivity::class.java)
            intent.putExtra("movieId",result!!.id.toString())
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        getMoviesDetail(movieid.toString())
        setContentView(binding.root)
    }

    fun getMoviesDetail(movieid:String) {
        val params: MutableMap<String, String> = HashMap()
        params["api_key"] = "7916ace8a965a1c3413cd5231af30364"
        params["language"] = "en-US"
        RetrofitInstance.api.getDetailMovies(movieid,params).enqueue(object  :
            Callback<DetailMovieModel> {
            override fun onResponse(call: Call<DetailMovieModel>, response: Response<DetailMovieModel>) {
                if (response.body()!=null){
                    result= response.body()!!
                    showItem(result!!)

                }
                else{
                    Log.d("TAGihan", response.code().toString())

                }
            }
            override fun onFailure(call: Call<DetailMovieModel>, t: Throwable) {

                Log.d("TAGihan", t.message.toString())
            }
        })
    }
    fun getTrailerDetail(movieid:String) {
        val params: MutableMap<String, String> = HashMap()
        params["api_key"] = "7916ace8a965a1c3413cd5231af30364"
        params["language"] = "en-US"
        RetrofitInstance.api.getDetailVideosMovies(movieid,params).enqueue(object  :
            Callback<ResponseVideosTrailer> {
            override fun onResponse(call: Call<ResponseVideosTrailer>, response: Response<ResponseVideosTrailer>) {
                if (response.body()!=null){
                   val idvideos= response.body()!!.trailer[0].key

                    val uri =Uri.parse("https://www.youtube.com/watch?v=$idvideos")
                    startActivity(Intent(intent.action,uri))


                }
                else{
                    Log.d("TAGihan", response.code().toString())

                }
            }
            override fun onFailure(call: Call<ResponseVideosTrailer>, t: Throwable) {

                Log.d("TAGihan", t.message.toString())
            }
        })
    }

    fun showItem(moviedetail : DetailMovieModel)
    {
        binding.textViewIsiOverView.text = moviedetail.overview
        binding.textViewtitle.text = moviedetail.title
        binding.textViewIsiReleaseDate.text = moviedetail.releaseDate

        Glide.with(binding.imageMovie)
            .load("https://image.tmdb.org/t/p/w500" + moviedetail.posterPath)
            .into(binding.imageMovie)
        Glide.with(binding.imageBackdrop)
            .load("https://image.tmdb.org/t/p/w500" + moviedetail.backdropPath)
            .into(binding.imageBackdrop)


        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0)
        format.setCurrency(Currency.getInstance("USD"))

        val k :String=format.format(moviedetail.revenue).toString()
        binding.textViewIsiRevenue.text = k
        binding.textViewStatus.text = moviedetail.status

        lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        productionAdapter = ProductionAdapter(this,moviedetail.productionCompanies)

        binding.mRecyclerViewProduction.setHasFixedSize(true)
        binding.mRecyclerViewProduction.layoutManager = lm


        binding.mRecyclerViewProduction.adapter = productionAdapter







    }




}